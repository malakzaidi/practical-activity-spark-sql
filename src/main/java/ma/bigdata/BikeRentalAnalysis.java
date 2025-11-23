package ma.bigdata;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class BikeRentalAnalysis {

    public static void main(String[] args) {
        // Créer une SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("Bike Sharing Analysis")
                .master("spark://spark-master-bike:7077")  // Connexion au cluster Spark
                .config("spark.submit.deployMode", "client")
                .getOrCreate();

        // Définir le niveau de log à WARN pour réduire les messages
        spark.sparkContext().setLogLevel("WARN");

        System.out.println("=== SPARK SQL - BIKE SHARING ANALYSIS ===\n");

        // ====================================
        // 1. DATA LOADING & EXPLORATION
        // ====================================
        System.out.println("1. DATA LOADING & EXPLORATION");
        System.out.println("----------------------------------------");

        // 1.1 Charger le CSV depuis HDFS
        Dataset<Row> bikeDF = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("hdfs://namenode:8020/input-data/bike_sharing.csv");

        System.out.println("✓ Dataset chargé avec succès\n");

        // 1.2 Afficher le schéma
        System.out.println("1.2 Schéma du DataFrame:");
        bikeDF.printSchema();

        // 1.3 Afficher les 5 premières lignes
        System.out.println("\n1.3 Les 5 premières lignes:");
        bikeDF.show(5);

        // 1.4 Nombre total de locations
        long totalRentals = bikeDF.count();
        System.out.println("1.4 Nombre total de locations: " + totalRentals + "\n");

        // ====================================
        // 2. CREATE TEMPORARY VIEW
        // ====================================
        System.out.println("\n2. CREATE TEMPORARY VIEW");
        System.out.println("----------------------------------------");
        bikeDF.createOrReplaceTempView("bike_rentals_view");
        System.out.println("✓ Vue temporaire 'bike_rentals_view' créée\n");

        // ====================================
        // 3. BASIC SQL QUERIES
        // ====================================
        System.out.println("\n3. BASIC SQL QUERIES");
        System.out.println("----------------------------------------");

        // 3.1 Locations de plus de 30 minutes
        System.out.println("3.1 Locations de plus de 30 minutes:");
        Dataset<Row> longRentals = spark.sql(
                "SELECT * FROM bike_rentals_view WHERE duration_minutes > 30"
        );
        longRentals.show();

        // 3.2 Locations démarrant à Station A
        System.out.println("\n3.2 Locations démarrant à 'Station A':");
        Dataset<Row> stationARentals = spark.sql(
                "SELECT * FROM bike_rentals_view WHERE start_station = 'Station A'"
        );
        stationARentals.show();

        // 3.3 Revenu total
        System.out.println("\n3.3 Revenu total:");
        Dataset<Row> totalRevenue = spark.sql(
                "SELECT SUM(price) as total_revenue FROM bike_rentals_view"
        );
        totalRevenue.show();

        // ====================================
        // 4. AGGREGATION QUERIES
        // ====================================
        System.out.println("\n4. AGGREGATION QUERIES");
        System.out.println("----------------------------------------");

        // 4.1 Nombre de locations par station de départ
        System.out.println("4.1 Nombre de locations par station de départ:");
        Dataset<Row> rentalsByStation = spark.sql(
                "SELECT start_station, COUNT(*) as rental_count " +
                        "FROM bike_rentals_view " +
                        "GROUP BY start_station " +
                        "ORDER BY rental_count DESC"
        );
        rentalsByStation.show();

        // 4.2 Durée moyenne par station
        System.out.println("\n4.2 Durée moyenne de location par station de départ:");
        Dataset<Row> avgDurationByStation = spark.sql(
                "SELECT start_station, " +
                        "ROUND(AVG(duration_minutes), 2) as avg_duration_minutes " +
                        "FROM bike_rentals_view " +
                        "GROUP BY start_station " +
                        "ORDER BY avg_duration_minutes DESC"
        );
        avgDurationByStation.show();

        // 4.3 Station avec le plus de locations
        System.out.println("\n4.3 Station avec le plus grand nombre de locations:");
        Dataset<Row> topStation = spark.sql(
                "SELECT start_station, COUNT(*) as rental_count " +
                        "FROM bike_rentals_view " +
                        "GROUP BY start_station " +
                        "ORDER BY rental_count DESC " +
                        "LIMIT 1"
        );
        topStation.show();

        // ====================================
        // 5. TIME-BASED ANALYSIS
        // ====================================
        System.out.println("\n5. TIME-BASED ANALYSIS");
        System.out.println("----------------------------------------");

        // 5.1 Extraire l'heure de start_time
        System.out.println("5.1 Extraction de l'heure de start_time:");
        Dataset<Row> withHour = spark.sql(
                "SELECT rental_id, start_time, " +
                        "HOUR(start_time) as rental_hour " +
                        "FROM bike_rentals_view"
        );
        withHour.show(5);

        // 5.2 Nombre de locations par heure (heures de pointe)
        System.out.println("\n5.2 Nombre de locations par heure (heures de pointe):");
        Dataset<Row> rentalsByHour = spark.sql(
                "SELECT HOUR(start_time) as hour, " +
                        "COUNT(*) as rental_count " +
                        "FROM bike_rentals_view " +
                        "GROUP BY HOUR(start_time) " +
                        "ORDER BY hour"
        );
        rentalsByHour.show();

        // 5.3 Station la plus populaire le matin (7-12h)
        System.out.println("\n5.3 Station la plus populaire le matin (7-12h):");
        Dataset<Row> morningPopular = spark.sql(
                "SELECT start_station, COUNT(*) as rental_count " +
                        "FROM bike_rentals_view " +
                        "WHERE HOUR(start_time) >= 7 AND HOUR(start_time) <= 12 " +
                        "GROUP BY start_station " +
                        "ORDER BY rental_count DESC " +
                        "LIMIT 1"
        );
        morningPopular.show();

        // ====================================
        // 6. USER BEHAVIOR ANALYSIS
        // ====================================
        System.out.println("\n6. USER BEHAVIOR ANALYSIS");
        System.out.println("----------------------------------------");

        // 6.1 Âge moyen des utilisateurs
        System.out.println("6.1 Âge moyen des utilisateurs:");
        Dataset<Row> avgAge = spark.sql(
                "SELECT ROUND(AVG(age), 2) as average_age " +
                        "FROM bike_rentals_view"
        );
        avgAge.show();

        // 6.2 Nombre d'utilisateurs par genre
        System.out.println("\n6.2 Nombre d'utilisateurs par genre:");
        Dataset<Row> usersByGender = spark.sql(
                "SELECT gender, COUNT(*) as user_count " +
                        "FROM bike_rentals_view " +
                        "GROUP BY gender"
        );
        usersByGender.show();

        // 6.3 Groupe d'âge qui loue le plus
        System.out.println("\n6.3 Groupe d'âge qui loue le plus de vélos:");
        Dataset<Row> ageGroups = spark.sql(
                "SELECT " +
                        "CASE " +
                        "    WHEN age BETWEEN 18 AND 30 THEN '18-30' " +
                        "    WHEN age BETWEEN 31 AND 40 THEN '31-40' " +
                        "    WHEN age BETWEEN 41 AND 50 THEN '41-50' " +
                        "    WHEN age >= 51 THEN '51+' " +
                        "    ELSE 'Other' " +
                        "END as age_group, " +
                        "COUNT(*) as rental_count " +
                        "FROM bike_rentals_view " +
                        "GROUP BY age_group " +
                        "ORDER BY rental_count DESC"
        );
        ageGroups.show();

        System.out.println("\n=== ANALYSE TERMINÉE ===");

        // Fermer la session Spark
        spark.stop();
    }
}