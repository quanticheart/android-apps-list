package quanticheart.com.packagelist

import android.content.Context
import android.content.pm.PackageManager
import java.util.*

object PackageUtils {
    fun getInstalledApps(context: Context, getSysPackages: Boolean = true, searchByName: String = ""): ArrayList<PackageInfo> {
        val res = ArrayList<PackageInfo>()
        val packs = context.packageManager.getInstalledPackages(0)
        val pm = context.applicationContext.packageManager

        for (information in packs) {
            if (!getSysPackages && information.versionName == null) {
                continue
            }
            if (searchByName == "") {
                res.add(getPackageInfo(information, context, pm))
            } else {
                if (information.packageName.contains(searchByName)) {
                    res.add(getPackageInfo(information, context, pm))
                }
            }
        }
        return res
    }

    private fun getPackageInfo(information: android.content.pm.PackageInfo, context: Context, pm: PackageManager): PackageInfo {
        val newInfo = PackageInfo()
        newInfo.appname = information.applicationInfo.loadLabel(context.packageManager).toString()
        newInfo.pname = information.packageName
        newInfo.versionName = information.versionName
        newInfo.versionCode = information.versionCode
        newInfo.icon = information.applicationInfo.loadIcon(context.packageManager)
        newInfo.intentStart = pm.getLaunchIntentForPackage(information.packageName)
        return newInfo
    }
}