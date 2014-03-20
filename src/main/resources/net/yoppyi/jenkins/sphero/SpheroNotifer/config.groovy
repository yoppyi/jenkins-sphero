package net.yoppyi.jenkins.sphero.SpheroNotifer;
f=namespace(lib.FormTagLib)

if (descriptor.installations.length != 0) {
	f.entry(title:_("Module")) {
		select(class:"setting-input",name:"module.moduleName") {
			descriptor.installations.each {
				f.option(selected:it.name==instance?.module?.name, value:it.name, it.name)
			}
		}
	}
}

//f.entry(title:_("Targets"),field:"targets") {
//    f.expandableTextbox()
//}
f.advanced {
	f.section(title:_("Ext Script"))
	f.entry(title:_("SUCCESS"),field:"success") { f.expandableTextbox() }
	f.entry(title:_("UNSTABLE"),field:"unstable") { f.expandableTextbox() }
	f.entry(title:_("FAILURE"),field:"failure") { f.expandableTextbox() }
	f.entry(title:_("NOT_BUILT"),field:"notBuilt") { f.expandableTextbox() }
	f.entry(title:_("ABORTED"),field:"aborted") { f.expandableTextbox() }
}
