package tastywine


// S3 connection - DOESNT WORK; ITS GARBAGE
import com.amazonaws._
//import com.google.common._
import org.apache.hadoop._

// Spark Base
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

// Spark ML
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// Spark SQL
// import org.apache.spark.sql.DataFrame
// import org.apache.spark.sql.DataFrameReader
// import org.apache.spark.sql.Dataset
// import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.StructType
//import org.apache.spark.sql.functions.




object TastyWine {
	
	// spark is dumb and I have to create a session before loading anything in
	def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
	  .appName("TastyWine")
    .master("local[*]")
    .getOrCreate()


      
// "fixed_acidity","volatile_acidity","citric_acid","residual_sugar","chlorides","free_sulfur_dioxide","total_sulfur_dioxide","density","pH","sulphates","alcohol","quality"
  val df_cols = Array("fixed_acidity","volatile_acidity","citric_acid","residual_sugar","chlorides","free_sulfur_dioxide","total_sulfur_dioxide","density","pH","sulphates","alcohol","quality")

    	// setting S3 paths
		  val training_path = "/home/ubuntu/TrainingDataset.csv"
      // val training_path = "s3://wine-qual/TrainingDataset.csv"

      val training = spark.read
        .format("s3selectCSV") //aws
        .option("delimiter", ";")
        .option("inferSchema","true")
        .option("header", "true")
        .csv(training_path) //local
        // .load(training_path) //s3
        .toDF(df_cols)

      //val training = spark.createDataFrame(training_load,schema)

      
    	// setting S3 paths
		  val validate_path = "/home/ubuntu/ValidationDataset.csv" 
      // val validate_path = "s3://wine-qual/ValidationDataset.csv"

      val validate = spark.read
        // .format("s3selectCSV") //aws
        .option("delimiter", ";")
        .option("inferSchema","true")
        .option("header", "true")
        .csv(validate_path) //local
        // .load(validate_path) //aws
        .format("libsvm")
        .toDF(df_cols)

      //val validate = spark.createDataFrame(validate_load,schema)

      
      
    val indexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexed")
      .setMaxCategories(10)

  val lr = new LogisticRegression()
    .setMaxIter(10)
    .setRegParam(0.3)
    .setElasticNetParam(0.8)

  // Fit the model
  val lrModel = lr.fit(training)

  // Print the coefficients and intercept for logistic regression
  println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

  // We can also use the multinomial family for binary classification
      spark.stop()
  	}
	



/*	

	reading in as text for quick ETL to fix col names
	val training = spark.read.textFile(training_path) //('\"\"\"\"','\"')
	val validate = spark.read.textFile(validate_path)

	//reading in as .CSVs with proper headers
	val training = spark.read.option("delimiter", ";").option("header", "true").csv(training_path)
	val validate = spark.read.option("delimiter", ";").option("header", "true").csv(validate_path)

	training.show()
	println()
	validate.show()
*/	

}
