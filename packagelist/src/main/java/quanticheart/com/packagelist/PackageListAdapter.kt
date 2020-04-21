package quanticheart.com.packagelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_item.view.*

class PackageListAdapter(private val recyclerView: RecyclerView, private val callback: (PackageInfo) -> Unit) : RecyclerView.Adapter<PackageListAdapter.PackagesHolder>() {
    private val arrayList: ArrayList<PackageInfo> = ArrayList()

    init {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@PackageListAdapter
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PackagesHolder =
        PackagesHolder(LayoutInflater.from(recyclerView.context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: PackagesHolder, i: Int) {
        viewHolder.bindView(arrayList[i])
    }

    override fun getItemCount(): Int = arrayList.size

    fun getAllList(getSysApps: Boolean = true) {
        arrayList.clear()
        arrayList.addAll(PackageUtils.getInstalledApps(recyclerView.context, getSysApps))
        notifyDataSetChanged()
    }

    fun getAllListByName(appName: String, getSysApps: Boolean = true) {
        arrayList.clear()
        arrayList.addAll(PackageUtils.getInstalledApps(recyclerView.context, getSysApps, appName))
        notifyDataSetChanged()
    }

    inner class PackagesHolder internal constructor(var view: View) : ViewHolder(view) {
        fun bindView(info: PackageInfo) {
            itemView.icon.setImageDrawable(info.icon)
            itemView.line1.text = info.appname
            itemView.line2.text = info.pname
            itemView.line3.text = info.versionName
            itemView.setOnClickListener {
                callback(info)
            }
        }
    }
}