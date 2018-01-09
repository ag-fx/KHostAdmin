package org.b3log.zephyr.views

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableView
import org.b3log.zephyr.controller.ProfileController
import org.b3log.zephyr.views.model.Host
import org.b3log.zephyr.views.model.Profile
import tornadofx.*
import java.util.*

class ProfileEditor : View() {
    val controller: ProfileController by inject()

    var hostTable: TableView<Host> by singleAssign()

    override val root = form {
        prefWidth = 400.0
        fieldset("Host List") {
            vbox(5.0) {
                tableview<Host> {
                    hostTable = this
                    isEditable = true
                    smartResize()
                    column("", Host::enable).useCheckbox(editable = true).minWidth(20)
                    column("IP地址", Host::ip).minWidth(150)
                    column("域名", Host::domain).minWidth(150)
                    itemsProperty().bind(controller.selectedProfile.hosts)
                }
                buttonbar {
                    button("Add Profile") {
                        setOnAction {
                            dialog("Add new profile") {
                                minWidth = 200.0
                                prefWidth = 200.0
                                val model = ViewModel()
                                val profile = model.bind { SimpleStringProperty() }
                                field("Profile") {
                                    textfield(profile) {
                                        required()
                                    }
                                }
                                buttonbar {
                                    button("Save").action {
                                        model.commit { saveProfile(profile.value) }
                                    }
                                }
                            }
                        }
                    }
                    button("Add Host") {
                        setOnAction {
                            //todo 我脚的这样写不太对吼。。。
                            dialog("添加Host(默认启用)") {
                                minWidth = 200.0
                                prefWidth = 200.0
                                val model = ViewModel()
                                val ip = model.bind { SimpleStringProperty() }
                                val domain = model.bind { SimpleStringProperty() }
                                field("IP") {
                                    textfield(ip) {
                                        required()
                                    }
                                }
                                field("域名") {
                                    textfield(domain) {
                                        required()
                                    }
                                }
                                buttonbar {
                                    button("Save").action {
                                        model.commit { saveHost(ip.value, domain.value) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveProfile(profile:String){
        controller.profiles.add(Profile(controller.profiles.size,profile, Arrays.asList()))
    }

    private fun saveHost(ip: String, domain: String) {
        val newHost = Host(true, ip, domain, controller.selectedProfile.id.value)
        controller.selectedProfile.hosts.value.add(newHost)
        hostTable.selectionModel.select(newHost)
        hostTable.edit(hostTable.items.size - 1, hostTable.columns.first())
    }
}
