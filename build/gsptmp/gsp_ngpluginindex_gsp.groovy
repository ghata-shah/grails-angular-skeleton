import grails.plugins.metadata.GrailsPlugin
import org.grails.gsp.compiler.transform.LineNumber
import org.grails.gsp.GroovyPage
import org.grails.web.taglib.*
import org.grails.taglib.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_ngpluginindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(3)
invokeTag('link','asset',7,['rel':("icon"),'href':("favicon.ico"),'type':("image/x-ico")],-1)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(1)
createTagBody(2, {->
printHtmlPart(5)
expressionOut.print(grails.util.Environment.current.name)
printHtmlPart(6)
expressionOut.print(grailsApplication.config.grails?.profile)
printHtmlPart(7)
invokeTag('meta','g',17,['name':("info.app.version")],-1)
printHtmlPart(8)
invokeTag('meta','g',21,['name':("info.app.grailsVersion")],-1)
printHtmlPart(9)
expressionOut.print(GroovySystem.getVersion())
printHtmlPart(10)
expressionOut.print(System.getProperty('java.version'))
printHtmlPart(11)
expressionOut.print(grails.util.Environment.reloadingAgentEnabled)
printHtmlPart(12)
expressionOut.print(grailsApplication.controllerClasses.size())
printHtmlPart(13)
expressionOut.print(grailsApplication.domainClasses.size())
printHtmlPart(14)
expressionOut.print(grailsApplication.serviceClasses.size())
printHtmlPart(15)
expressionOut.print(grailsApplication.tagLibClasses.size())
printHtmlPart(16)
for( plugin in (applicationContext.getBean('pluginManager').allPlugins) ) {
printHtmlPart(17)
expressionOut.print(plugin.name)
printHtmlPart(18)
expressionOut.print(plugin.version)
printHtmlPart(19)
}
printHtmlPart(20)
})
invokeTag('captureContent','sitemesh',46,['tag':("nav")],2)
printHtmlPart(21)
invokeTag('image','asset',50,['src':("grails-cupsonly-logo-white.svg"),'class':("grails-logo")],-1)
printHtmlPart(22)
for( c in (grailsApplication.controllerClasses.sort { it.fullName }) ) {
printHtmlPart(23)
createTagBody(3, {->
expressionOut.print(c.fullName)
})
invokeTag('link','g',70,['controller':(c.logicalPropertyName)],3)
printHtmlPart(24)
}
printHtmlPart(25)
})
invokeTag('captureBody','sitemesh',78,[:],1)
printHtmlPart(26)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1460373726962L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'none'
public static final String TAGLIB_CODEC = 'none'
}
