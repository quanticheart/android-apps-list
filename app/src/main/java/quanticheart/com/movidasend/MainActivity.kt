package quanticheart.com.movidasend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import quanticheart.com.packagelist.PackageListAdapter
import quanticheart.com.packages.R

class MainActivity : AppCompatActivity() {
    private var sysApps = true
    private var name = ""
    private var pkAdapter: PackageListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        pkAdapter = PackageListAdapter(recycler) {
            try {
                startActivity(it.intentStart)
            } catch (e: Exception) {
                Toast.makeText(this, "Erro to open app", Toast.LENGTH_SHORT).show()
            }
        }

        switchSys.setOnCheckedChangeListener { _, b ->
            sysApps = b
            filter()
        }

        search.setTextListener {
            name = it
            filter()
        }
        filter()
    }

    private fun filter() {
        if (name.isNotEmpty()) {
            pkAdapter?.getAllListByName(name, sysApps)
        } else {
            pkAdapter?.getAllList(sysApps)
        }
    }
}