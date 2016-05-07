package ngplugin


import org.apache.commons.lang.StringUtils;
import org.grails.core.artefact.DomainClassArtefactHandler;

import grails.dev.commands.*
import grails.validation.ConstrainedProperty;

class CreateNgViewCommand implements ApplicationCommand {
	
    boolean handle(ExecutionContext ctx) {
		def args = ctx.commandLine.remainingArgs
		def grailsApp = grails.util.Holders.grailsApplication
		def domainClass = grailsApp.getDomainClass(args[0])
		if(!domainClass) {
			println "| Error: Domain class with name " + args[0] + " not found. Please use fully qualified name."
			return true
		}
		def className = domainClass.getName()
		def packageName = domainClass.getPackageName()
		def domainProperties = domainClass?.getPersistentProperties()
		def constrainedProperties = domainClass?.getConstrainedProperties()
				
		File createFile = generateCreateView(grailsApp, packageName, className, domainProperties, constrainedProperties)
		if(createFile.exists()) {
			println '| File created at ' + createFile.getPath()
		}
		
		File listFile = generateListView(grailsApp, packageName, className, domainProperties)
		if(listFile.exists()) {
			println '| File created at ' + listFile.getPath()
		}
		
		File showFile = generateShowView(grailsApp, packageName, className, domainProperties)
		if(showFile.exists()) {
			println '| File created at ' + showFile.getPath()
		}
		
        return true
    }
	
	def generateCreateView(grailsApp, packageName, className, domainProperties, constrainedProperties){
		File file = createFileForView('create.tpl.html', className, packageName)
		writeToCreateTemplate(domainProperties, file, className, grailsApp, constrainedProperties)
		return file
	}
	
	def generateListView(grailsApp, packageName, className, domainProperties) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		def fileName = lowerCaseClassName + '.tpl.html'
		File file = createFileForView(fileName, className, packageName)
		WriteToListTemplate(domainProperties, file, className, grailsApp)
	}
	
	def generateShowView(grailsApp, packageName, className, domainProperties){
		File file = createFileForView('show.tpl.html', className, packageName)
		writeToShowTemplate(domainProperties, file, className)
		return file
	}
	
	def createFileForView(fileName, className, packageName){
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		String baseDir = System.getProperty("user.dir")
		String dirPath = baseDir + File.separator + 'grails-app' + File.separator + 'assets' + File.separator + 'javascripts' + File.separator
		def packages = packageName.split('[.]')
		packages?.each{pName ->
			dirPath += pName + File.separator
		}
		dirPath += lowerCaseClassName + File.separator + 'templates'
		new File(dirPath).mkdirs()
		String filePath = dirPath + File.separator + fileName
		new File(filePath)
	}
	
	def writeToCreateTemplate(domainProperties, File file, className, grailsApp, constrainedProperties) {
		file.write '<div class="col s12 m12 l12">\n'
		file << '<div class="card">\n'
		file << '<div class="card-content">\n'
		file << '<div class="card-title">Create New ' + className +'</div>\n'
		file << '<div class="row ">\n'
		file << '<div class="col s12 m12">\n'
        file << '<form>\n'
		createInputsForDomain(domainProperties, file, className, grailsApp, constrainedProperties)
		file << '</form>\n'
		file << '</div>\n'
		file << '</div>\n'
		file << '</div>\n'
		file << '</div>\n'
		file << '</div>\n'
		
		return file
	}
	
	def createInputsForDomain(domainProperties, File file, className, grailsApp, constrainedProperties) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		domainProperties?.each{ prop ->
			String required = ''
			if(constrainedProperties.containsKey(prop.name)){
				ConstrainedProperty cp = (ConstrainedProperty) constrainedProperties.get(prop.name);
				if(!cp.isNullable()){
					required = 'required'
				}
			}
			if(prop.type == String || prop.type instanceof Number){
				file << '<div class="input-field col s12 m6">\n'
				file << '	<input ng-model="ctrl.' +lowerCaseClassName + '.' +prop.name + '" id="'+prop.name+'" type="text" '+required+'>\n'
				file << '	<label for="'+prop.name+'">'+splitCamelCase(prop.name?.capitalize()) +'</label>\n'
				file << '</div>\n'
			} else if(prop.type == Date) {
				file << '<div class="input-field col s12 m6">\n'
				file << '	<input ng-model="ctrl.' +lowerCaseClassName + '.' +prop.name + '" id="'+prop.name+'" type="date" class="datepicker" '+required+'>\n'
				file << '	<label for="'+prop.name+'" class="active">'+splitCamelCase(prop.name?.capitalize())+'</label>\n'
				file << '</div>\n'
			} else if(prop.type == Boolean) {
				file << '<div class="input-field col s12 m6">\n'
				file << '	<input ng-model="ctrl.'+lowerCaseClassName + '.' +prop.name +'" id="'+ prop.name +'" type="checkbox" '+required+'/>\n'
				file << '	<label for="'+ prop.name +'" class="align">'+splitCamelCase(prop.name?.capitalize())+'</label>\n'
				file << '</div>\n'
			} else if(prop.type.isEnum()) {
				file << '<div class="input-field col s12 m6">\n'
				file << '	<select id="'+ prop.name +'" ng-model="ctrl.'+lowerCaseClassName + '.' +prop.name +'">\n'
				file << '		<option value="">'+splitCamelCase(prop.name?.capitalize())+'</option>\n'
				prop?.type?.enumConstantsShared?.each{ ec ->
					file << '		<option value="'+ec+'">'+ec?.value+'</option>\n'
				}
				file << '	</select>\n'
				file << '</div>\n'
			} else if(grailsApp.isDomainClass(prop.type)){
				/*def childClass = grailsApp.getDomainClass(prop.type?.getName())
				List subDomainList = []
				childClass.clazz?.withNewSession { session ->
					subDomainList = childClass.clazz?.findAll()
				}
				file << '<div class="input-field col s12 m6">\n'
				file << '	<select id="'+ prop.name +'" ng-model="ctrl.'+lowerCaseClassName + '.' +prop.name +'">\n'
				file << '		<option value="">'+prop.name?.capitalize()+'</option>\n'
				subDomainList?.each{subDomain ->
					file << '		<option value="'+subDomain?.id+'">'+subDomain?.toString()+'</option>\n'
				}
				file << '	</select>\n'
				file << '</div>\n'*/
			} else if(prop.type instanceof Collection) {} else {
				file << '<div class="input-field col s12 m6">\n'
				file << '	<input ng-model="ctrl.' +lowerCaseClassName + '.' +prop.name + '" id="'+prop.name+'" type="text" '+required+'>\n'
				file << '	<label for="'+prop.name+'">'+splitCamelCase(prop.name?.capitalize())+'</label>\n'
				file << '</div>\n'
			}
		}
		file << '<div class="input-field col s12 m12 center">\n'
		file << '	<button class="btn waves-effect waves-light" type="submit" name="action" ng-click="ctrl.save(ctrl.'+lowerCaseClassName+')" >Submit\n'
		file << '	<i class="material-icons right">send</i>\n'
		file << '	</button>\n'
		file << '</div>\n'
	}
	
	def WriteToListTemplate(domainProperties, file, className, grailsApp) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file.write '<div class="card">\n'
		file << '<div class="card-content">\n'
		file << '	<div class="card-title">' + className +' List</div>\n'
		file << '	<table>\n'
		file << '		<tr>\n'
		domainProperties?.each{ prop ->
			file << '			<th>'+splitCamelCase(prop.name?.capitalize())+'</th>\n'
		}
		file << '		</tr>\n'
		file << '		<tr ng-repeat="'+lowerCaseClassName+' in ctrl.'+lowerCaseClassName+'s">\n'
		domainProperties?.each{ prop ->
			file << '		<td ng-bind="'+lowerCaseClassName+ '.'+prop.name+'"> </td>\n'
		}
		file << '		</tr>\n'
		file << '	</table>\n'
		file << '</div>\n'
		file << '</div>\n'
		
		return file
	}

	def writeToShowTemplate(domainProperties, file, className) {
		def lowerCaseClassName = StringUtils.uncapitalize(className)
		file.write '<div class="card">\n'
		file << '<div class="card-content">\n'
		file << '	<div class="card-title">' + className +' List</div>\n'
		domainProperties?.each{ prop ->
			file << prop.name?.capitalize() + ': {{ctrl.' + lowerCaseClassName + '.' + prop.name + '}} <br/>\n'
		}
		file << '<a href="#/'+lowerCaseClassName+'/edit/{{ctrl.'+lowerCaseClassName+'.id}}" class="btn">Edit</a>\n'
		file << '<input class="btn red white-text" type="submit" ng-click="ctrl.delete('+lowerCaseClassName+')" value="Delete" />\n'
		file << '</div>\n'
		file << '</div>\n'
	}
	static String splitCamelCase(String s) {
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
				),
				" "
		);
	}
}
