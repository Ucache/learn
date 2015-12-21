import org.apache.spark.mllib.classification.{LogisticRegressionWithLBFGS,LogisticRegressionModel}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
object test  {
	def main() = {
		val data = MLUtils.loadLibSvmFile(sc,"data1.svm")
		val splits = data.randomSplit(Array(0.6,0.4), seed=42L)
		val training = splits(0)
		val test = splits(1)

		val numIter = 100
		val model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(training)

		val predictionAndLabels = test.map{ case LabeledPoint(label, features) => 
			val prediction = model.predict(features)
			(prediction,label)
			}

		val metrics = new MulticlassMetrics(predictionAndLabels)
		val precision = metrics.precision
		println("precision = " + precision)
	}
}


