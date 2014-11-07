<!DOCTYPE html>
<html>
  <head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Architects+Daughter' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="stylesheets/stylesheet.css" media="screen">
    <link rel="stylesheet" type="text/css" href="stylesheets/pygment_trac.css" media="screen">
    <link rel="stylesheet" type="text/css" href="stylesheets/print.css" media="print">

    <!--[if lt IE 9]>
    <script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <title>Cloudflareupdater by vasilevich</title>
  </head>

  <body>
    <header>
      <div class="inner">
        <h1>Cloudflareupdater</h1>
        <h2>This program allows you to automatically update the IP addresses of your domains based on the IP of your computer, much like the NO-IP dynamic dns service but with cloudflare hosted domains.</h2>
        <a href="https://github.com/vasilevich/CloudflareUpdater" class="button"><small>View project on</small> GitHub</a>
      </div>
    </header>

    <div id="content-wrapper">
      <div class="inner clearfix">
        <section id="main-content">
          <h3>
<a id="cloudflare-ips-updater" class="anchor" href="#cloudflare-ips-updater" aria-hidden="true"><span class="octicon octicon-link"></span></a>Cloudflare IP's Updater</h3>

<p>This program allows windows/linux(untested) users to automatically update the records in their domains 
using the IP of their computer.</p>

<h3>
<a id="how-to-use" class="anchor" href="#how-to-use" aria-hidden="true"><span class="octicon octicon-link"></span></a>How to use</h3>

<h4>
<a id="windows" class="anchor" href="#windows" aria-hidden="true"><span class="octicon octicon-link"></span></a>Windows</h4>

<h5>
<a id="option-1" class="anchor" href="#option-1" aria-hidden="true"><span class="octicon octicon-link"></span></a>Option 1:</h5>

<p>You can download the <a href="https://github.com/vasilevich/CloudflareUpdater/raw/master/bin/artifacts/Cloudflare_Updater_jar/CloudflareUpdater.exe">Executeable cloudflare updater</a>file (unsigned, and created using launch4j thus, the UAC warning).</p>

<h5>
<a id="option-2" class="anchor" href="#option-2" aria-hidden="true"><span class="octicon octicon-link"></span></a>Option 2:</h5>

<p>You can download the <a href="https://github.com/vasilevich/CloudflareUpdater/raw/master/bin/artifacts/Cloudflare_Updater_jar/Cloudflare%20Updater.jar">Jar runnable executeable cloudflare updater</a>file which works exactly like the executeable tho you can't give an icon directly to the jar.</p>

<h5>
<a id="option-3" class="anchor" href="#option-3" aria-hidden="true"><span class="octicon octicon-link"></span></a>Option 3:</h5>

<p>Clone the repository to your computer and compile the sources.</p>

<h4>
<a id="other-distros" class="anchor" href="#other-distros" aria-hidden="true"><span class="octicon octicon-link"></span></a>Other distros</h4>

<p>All previous options excpect for 1.</p>

<h4>
<a id="basic-requirements" class="anchor" href="#basic-requirements" aria-hidden="true"><span class="octicon octicon-link"></span></a>Basic Requirements:</h4>

<h5>
<a id="java-virtual-machine-similar-to-16-or-higherlesser-ones-might-work-but-havent-tested" class="anchor" href="#java-virtual-machine-similar-to-16-or-higherlesser-ones-might-work-but-havent-tested" aria-hidden="true"><span class="octicon octicon-link"></span></a>Java Virtual Machine similar to 1.6 or higher(Lesser ones might work, but haven't tested).</h5>

<h5>
<a id="windows-operating-system-if-you-run-the-executeable" class="anchor" href="#windows-operating-system-if-you-run-the-executeable" aria-hidden="true"><span class="octicon octicon-link"></span></a>Windows operating system if you run the executeable.</h5>

<h5>
<a id="how-to-use-1" class="anchor" href="#how-to-use-1" aria-hidden="true"><span class="octicon octicon-link"></span></a>How to use</h5>

<h6>
<a id="step-1" class="anchor" href="#step-1" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 1:</h6>

<p>Open the program</p>

<h6>
<a id="step-2" class="anchor" href="#step-2" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 2:</h6>

<p>put your cloudflare username, and token which can be obtained <a href="https://www.cloudflare.com/my-account">Cloudflare token</a>
right in your cloudflare my account panel.</p>

<h6>
<a id="step-3" class="anchor" href="#step-3" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 3</h6>

<p>Click obtain info a domains tree should be generated showing all the domains in your account.</p>

<h6>
<a id="step-4" class="anchor" href="#step-4" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 4</h6>

<p>Navigate to the domain you wish to edit.
In order to create a new auto updating record:
Click on the domain, and press space/enter and a popup should let you make a new record and continue Step 5.
In order to edit a record into an auto updating record:
Click on the record/or the insides of it and double click/press enter/ space and continue to Step 5.</p>

<h6>
<a id="step-5" class="anchor" href="#step-5" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 5</h6>

<p>in the popup choose type A record, or if its already A re-choose the A record or write yourself $$AutoIP and click ok.</p>

<h6>
<a id="step-6" class="anchor" href="#step-6" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 6</h6>

<p>In order to view the status of the auto updating and edit the update intervals click on File-&gt;Current Refreshing Operations</p>

<h6>
<a id="step-7" class="anchor" href="#step-7" aria-hidden="true"><span class="octicon octicon-link"></span></a>Step 7</h6>

<p>In order to edit the update interval double click the updating record.</p>

<h3>
<a id="notes" class="anchor" href="#notes" aria-hidden="true"><span class="octicon octicon-link"></span></a>Notes</h3>

<p>This program is just a tool and has no relations in any way with <a href="http://cloudflare.com">CloudFlare</a> nor does it have any affilliation, this program is made for the sole purpose of using the cloudflare API as a DYNAMIC DNS mainly for simple users.
You use this program at your own risk.</p>
        </section>

        <aside id="sidebar">
          <a href="https://github.com/vasilevich/CloudflareUpdater/zipball/master" class="button">
            <small>Download</small>
            .zip file
          </a>
          <a href="https://github.com/vasilevich/CloudflareUpdater/tarball/master" class="button">
            <small>Download</small>
            .tar.gz file
          </a>

          <p class="repo-owner"><a href="https://github.com/vasilevich/CloudflareUpdater"></a> is maintained by <a href="https://github.com/vasilevich">vasilevich</a>.</p>

          <p>This page was generated by <a href="https://pages.github.com">GitHub Pages</a> using the Architect theme by <a href="https://twitter.com/jasonlong">Jason Long</a>.</p>
        </aside>
      </div>
    </div>

  
  </body>
</html>