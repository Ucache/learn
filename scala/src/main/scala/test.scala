import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.SparkConf

object test  {
	def main(args: Array[String]) = {
		println("yo, this main calling")


		val conf = new SparkConf().setAppName("appName").setMaster("local[2]")
		val sc = new SparkContext(conf)
		// Load training data in LIBSVM format.
		val data = MLUtils.loadLibSVMFile(sc, "src/main/scala/data1.svm")

		// Split data into training (60%) and test (40%).
		val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
		val training = splits(0).cache()
		val test = splits(1)

		// Run training algorithm to build the model
		val numIterations = 100
		val model = SVMWithSGD.train(training, numIterations)

		// Clear the default threshold.
		model.clearThreshold()

		// Compute raw scores on the test set.
		val scoreAndLabels = test.map { point =>
		  val score = model.predict(point.features)
		  (score, point.label)
		}

		// Get evaluation metrics.
		val metrics = new BinaryClassificationMetrics(scoreAndLabels)
		val auROC = metrics.areaUnderROC()

		println("Area under ROC = " + auROC)
	}
}


