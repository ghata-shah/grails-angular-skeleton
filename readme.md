<h2>Summary</h2>
This is a Grails plugin that allows you to use Angular.js based scaffolding. It is written in Grails 3 and supports application of Grails 3. It will create angular templates, controller, service and module by executing just one command. You can achieve CRUD operation for your application in very less timing using this plugin.

<h3>Usage</h3>

After installing plugin, create any domain class in your application, if there isn't any. 

Lets say you have created MyDomain.groovy in com.foo package. Now, execute below grails command to create entire angular structure based on your domain.
<pre>grails create-ng-skeleton _domain-class-name-fully-qualified_ </pre>

For our example, you can execute command as:
<pre>grails create-ng-skeleton com.foo.MyDomain</pre>

It will create following files into your grails application:
<li>grails-app/assets/javascripts/_package_/_domain_/templates/create.tpl.html</li>
<li>grails-app/assets/javascripts/_package_/_domain_/templates/_domain_.tpl.html</li>
<li>grails-app/assets/javascripts/_package_/_domain_/templates/show.tpl.html</li>
<li>grails-app/assets/javascripts/_package_/_domain_/controllers/_domain_Controller.js</li>
<li>grails-app/assets/javascripts/_package_/_domain_/services/_domain_DataFactoryService.js</li>
<li>grails-app/assets/javascripts/_package_/_domain_/_domain_.js</li>
<li>grails-app/assets/javascripts/_package_/_domain_/routes.js</li>

Templates will include css classes of Materialize css framework.

Few other commands are also available by this plugin:
<pre>grails create-ng-view _domain-class-name-fully-qualified_ </pre>
<p>It will create only template files under grails-app/assets/javascripts/_package_/_domain_/templates folder structure.</p>
<pre>grails create-angular-controller _domain-class-name-fully-qualified_ </pre>
<p>It will create _domain_Controller.js file under grails-app/assets/javascripts/_package_/_domain_/controllers folder structure.</p>
<pre>grails create-angular-service _domain-class-name-fully-qualified_ </pre>
<p>It will create _domain_DataFactoryService.js file under grails-app/assets/javascripts/_package_/_domain_/services folder structure.</p>
<pre>grails create-angular-module _domain-class-name-fully-qualified_ </pre>
<p>It will create _domain_.js file under grails-app/assets/javascripts/_package_/_domain_ folder structure.</p>
<pre>grails create-angular-route _domain-class-name-fully-qualified_ </pre>
<p>It will create routes.js file under grails-app/assets/javascripts/_package_/_domain_ folder structure.</p>

<h3>Todo</h3>
<li>You need to add ng-view div in your index.gsp file</li>
<li>Add your module in app.js file.</li>
<li>Add bower dependency for angular-route in build.gradle, if there isn't.</li>

And that is it. Your angular part for CRUD has been done..!!
