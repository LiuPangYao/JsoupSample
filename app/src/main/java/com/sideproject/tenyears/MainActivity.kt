package com.sideproject.tenyears

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var asyncTask = WebInfoAsyncTask(this)
        asyncTask.execute(1)

    }

    companion object {
        class WebInfoAsyncTask internal constructor(context: MainActivity) : AsyncTask<Int, String, ArrayList<Product>?>() {

            val msg1 : TextView = context.findViewById(R.id.msg1)
            val msg2 : TextView = context.findViewById(R.id.msg2)
            val msg3 : TextView = context.findViewById(R.id.msg3)
            val msg4 : TextView = context.findViewById(R.id.msg4)
            val msg5 : TextView = context.findViewById(R.id.msg5)
            val msg6 : TextView = context.findViewById(R.id.msg6)

            val arrayProduct = ArrayList<Product>()
            val arrayTextView = ArrayList<TextView>()

            override fun onPreExecute() {
                super.onPreExecute()
                arrayTextView.add(msg1)
                arrayTextView.add(msg2)
                arrayTextView.add(msg3)
                arrayTextView.add(msg4)
                arrayTextView.add(msg5)
                arrayTextView.add(msg6)
            }

            override fun doInBackground(vararg params: Int?): ArrayList<Product> {

                val doc : Document = Jsoup.connect("https://www.cpc.com.tw/").get()

                for (i in 0..5) {

                    val product = Product()
                    val elementName: Elements = doc.select("div.today_price_info")

                    product.Name = elementName.get(i).text()

                    val elementImg : Elements = doc.getElementsByClass("today_price_img")

                    product.Path = "https://www.cpc.com.tw/" + (elementImg[i].childNode(0) as Element).attr("src")

                    product.Price = elementName[i].getElementsByClass("price").attr("id")

                    arrayProduct.add(product)
                }

                return arrayProduct
            }

            override fun onPostExecute(result: ArrayList<Product>?) {
                super.onPostExecute(result)

                if (result != null) {
                    for ((i) in result.withIndex()) {
                        arrayTextView[i].setText(result.get(i).Name + "\n"
                                                    + result.get(i).Price + "\n"
                                                        + result.get(i).path)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
