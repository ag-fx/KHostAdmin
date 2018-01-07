package org.b3log.zephyr.model

import javafx.collections.FXCollections
import tornadofx.ItemViewModel
import tornadofx.getProperty
import tornadofx.property

/**
 * 多个IP可能有同一个domain
 * 一个profile下有多个domain，每个profile下相同domian的ip只有一个
 */
class Host(enable: Boolean, ip: String, domain: String, profile: Int) {
    var enable by property(enable)
    fun enableProperty() = getProperty(Host::enable)

    var ip by property(ip)
    fun ipProperty() = getProperty(Host::ip)

    var domain by property(domain)
    fun domainProperty() = getProperty(Host::domain)

    var profile by property(profile)
    fun profileProperty() = getProperty(Host::profile)
}

/**
 * Host原文件会被保存为id=0，name=Main的Profile
 * 将对原文件做备份，如果没有执行export操作，使用完成时，将恢复原状
 * 再打开始，可以根据配置文件，重新使得之前生效的profile写入
 */
class Profile(id: Int,name: String,hosts : List<Host>){
    var id by property(id)
    fun idProperty() = getProperty(Profile::id)

    var name by property(name)
    fun nameProperty() = getProperty(Profile::name)

    var hosts by property(FXCollections.observableArrayList(hosts))
    fun hostsProperty() = getProperty(Profile::hosts)
}

class ProfileModel : ItemViewModel<Profile>(){
    val id = bind {item?.idProperty()}
    val name = bind {item?.nameProperty()}
    val hosts = bind {item?.hostsProperty()}
}
