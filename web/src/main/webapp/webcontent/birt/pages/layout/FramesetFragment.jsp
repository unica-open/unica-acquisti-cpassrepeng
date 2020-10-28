<%-----------------------------------------------------------------------------
	Copyright (c) 2004-2008 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" buffer="none" %>
<%@ page import="org.eclipse.birt.report.presentation.aggregation.IFragment,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.resource.ResourceConstants,
				 org.eclipse.birt.report.resource.BirtResources,
				 org.eclipse.birt.report.utility.ParameterAccessor" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%
	// base href can be defined in config file for deployment.
	/* String baseHref = request.getScheme( ) + "://" + request.getServerName( ) + ":" + request.getServerPort( );
	if( !attributeBean.isDesigner( ) )
	{
		String baseURL = ParameterAccessor.getBaseURL( );
		if( baseURL != null )
			baseHref = baseURL;
	}
	baseHref += request.getContextPath( ) + fragment.getJSPRootPath( ); */

	String baseHref = request.getContextPath( ) + fragment.getJSPRootPath( );

%>

<%-----------------------------------------------------------------------------
	Viewer root fragment
-----------------------------------------------------------------------------%>

<!DOCTYPE html> 
<!--[if lt IE 7 ]><html class="ie6" lang="it" xmlns="http://www.w3.org/1999/xhtml"><![endif]--> 
<!--[if IE 7 ]><html class="ie7" lang="it" xmlns="http://www.w3.org/1999/xhtml"><![endif]--> 
<!--[if IE 8 ]><html class="ie8" lang="it" xmlns="http://www.w3.org/1999/xhtml"><![endif]--> 
<!--[if IE 9 ]><html class="ie9" lang="it" xmlns="http://www.w3.org/1999/xhtml"><![endif]--> 
<!--[if (gte IE 9)|!(IE)]><!--> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<!--<![endif]--> 
<head> 
<meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	
	
		<BASE href="<%= baseHref %>" />
		
		
    <!--[if IE 9]><script type="text/javascript">
        // Fix for IE ignoring relative base tags.
        // See http://stackoverflow.com/questions/3926197/html-base-tag-and-local-folder-path-with-internet-explorer
        (function() {
            var baseTag = document.getElementsByTagName('base')[0];
            baseTag.href = baseTag.href;
        })();
    </script><![endif]-->   
    		
		<!-- Mimics Internet Explorer 7, it just works on IE8. -->


		<LINK REL="stylesheet" HREF="birt/styles/style.css" TYPE="text/css">
		<%
		if( attributeBean.isRtl() )
		{
		%>
		<LINK REL="stylesheet" HREF="birt/styles/dialogbase_rtl.css" MEDIA="screen" TYPE="text/css"/>
		<%
		}
		else
		{
		%>
		<LINK REL="stylesheet" HREF="birt/styles/dialogbase.css" MEDIA="screen" TYPE="text/css"/>	
		<%
		}
		%>
		<script type="text/javascript">			
			<%
			if( request.getAttribute("SoapURL") != null )
			{
			%>
			var soapURL = "<%= (String)request.getAttribute("SoapURL")%>";
			<%
			}
			else
			{
			%>
			var soapURL = document.location.href;
			<%
			}
			%>
			var rtl = <%= attributeBean.isRtl( ) %>;
		</script>
		

		
		
		<script src="birt/jquery/jquery-1.10.2.js" type="text/javascript"></script>
		
		<script src="birt/ajax/utility/Debug.js" type="text/javascript"></script>
		<script src="birt/ajax/lib/prototype.js" type="text/javascript"></script>
		
		<!-- Mask -->
		<script src="birt/ajax/core/Mask.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/BrowserUtility.js" type="text/javascript"></script>
		
		<!-- Drag and Drop -->
		<script src="birt/ajax/core/BirtDndManager.js" type="text/javascript"></script>
		
		<script src="birt/ajax/utility/Constants.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/BirtUtility.js" type="text/javascript"></script>
		
		<script src="birt/ajax/core/BirtEventDispatcher.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtEvent.js" type="text/javascript"></script>
		
		<script src="birt/ajax/mh/BirtBaseResponseHandler.js" type="text/javascript"></script>
		<script src="birt/ajax/mh/BirtGetUpdatedObjectsResponseHandler.js" type="text/javascript"></script>

		<script src="birt/ajax/ui/app/AbstractUIComponent.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/AbstractBaseToolbar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtToolbar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtNavigationBar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/AbstractBaseToc.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtToc.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtProgressBar.js" type="text/javascript"></script>

 		<script src="birt/ajax/ui/report/AbstractReportComponent.js" type="text/javascript"></script>
 		<script src="birt/ajax/ui/report/AbstractBaseReportDocument.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/report/BirtReportDocument.js" type="text/javascript"></script>

		<script src="birt/ajax/ui/dialog/AbstractBaseDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtTabedDialogBase.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/AbstractParameterDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtParameterDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtSimpleExportDataDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtExportReportDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtPrintReportDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtPrintReportServerDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/AbstractExceptionDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtExceptionDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtConfirmationDialog.js" type="text/javascript"></script>
		
		<script src="birt/ajax/utility/BirtPosition.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/Printer.js" type="text/javascript"></script>

		<script src="birt/ajax/core/BirtCommunicationManager.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtSoapRequest.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtSoapResponse.js" type="text/javascript"></script>
		
		




<%-- Caricamento del framework Bootstrap per JavaScript --%>
<script type="text/javascript" src="${jspathexternal}bootstrap-transition.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-alert.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-modal.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-dropdown.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-scrollspy.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-tab.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-popover.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-button.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-collapse.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-carousel.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-typeahead.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-affix.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-select.js"></script>
<script type="text/javascript" src="${jspathexternal}bootstrap-datepicker.js"></script>






 <!-- Le styles -->
    <link href="/ris/servizi/siac/css/bootstrap-contabilia.css" rel="stylesheet"/>
	<link href="/ris/servizi/siac/css/skin-contabilia.css" rel="stylesheet"/>
	<!-- <link href="/ris/servizi/siac/css/dashboard.css" rel="stylesheet"/> -->
	  
    <link href="/ris/servizi/siac/css/portalHeader.css" rel="stylesheet"/>
	<link href="/ris/servizi/siac/css/chosen/chosen.css" rel="stylesheet"/>
	

    <!--<link href="/ris/servizi/siac/css/less/bootstrap.css" rel="stylesheet"/>-->
	
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
	<!-- 
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../../docs/assets/ico/apple-touch-icon-144-precomposed.png"/>
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../../docs/assets/ico/apple-touch-icon-114-precomposed.png"/>
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../../docs/assets/ico/apple-touch-icon-72-precomposed.png"/>
    <link rel="apple-touch-icon-precomposed" href="../../docs/assets/ico/apple-touch-icon-57-precomposed.png"/>
    <link rel="shortcut icon" href="../../docs/assets/ico/favicon.png"/>
	-->
	
    
    <link rel="stylesheet" href="/ris/servizi/siac/css/tree/zTreeStyle.css" type="text/css"/>
    <link rel="stylesheet" href="/ris/servizi/siac/css/datatable/dataTable.bootstrap.css"/>
    <link rel="stylesheet" href="/ris/servizi/siac/css/bootstrap-select.min.css"/>



		
		
	</HEAD>
	
	<BODY 
		CLASS="BirtViewer_Body"  
		ONLOAD="javascript:init( );" 
		SCROLL="no" 
		LEFTMARGIN='0px' 
		STYLE='display:none; overflow:hidden; direction: <%= attributeBean.isRtl()?"rtl":"ltr" %>'
		>
		<!-- Header section -->
	
	

<%-- Gestione della navigazione --%>
<p class="nascosto">
	<a name="A-sommario" title="A-sommario"></a>
</p>

<ul id="sommario" class="nascosto">
	<li><a href="#A-contenuti">Salta ai contenuti</a></li>
</ul>
<%-- Termine navigazione --%>

<hr/>

<%-- Banner --%>
<div class="container-fluid-banner">

	<a name="A-contenuti" title="A-contenuti"></a>
	
</div>	
		
		
<!-- <div class="row-fluid"> -->
<!-- 		<div class="span12"> -->
<!-- 			<ul class="breadcrumb"> -->
<!-- 				<li><a href="xxx">Home</a><span -->
<!-- 					class="divider">></span></li> -->
<!-- 				<li><a href="/siacboapp/homeUtenti.do">Backoffice</a><span -->
<!-- 					class="divider">></span></li> -->
				
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
	
	
	
	
	
	
	
		

		<TABLE ID='layout' CELLSPACING='0' CELLPADDING='0' STYLE='width:100%;height:100%'>
			<%
				if( attributeBean.isShowTitle( ) )
				{
			%>
			<TR CLASS='body_caption_top'>
				<TD COLSPAN='2'></TD>
			</TR>
			<TR CLASS='body_caption' VALIGN='bottom'>
				<TD COLSPAN='2'>
					<TABLE BORDER=0 CELLSPACING="0" CELLPADDING="1px" WIDTH="100%">
						<TR>
							<TD WIDTH="3px" >&nbsp;</TD>
							<TD>
								<B><%= attributeBean.getReportTitle( ) %>
								</B>
							</TD>
							<TD ALIGN='right'>
							</TD>
							<TD WIDTH="3px" >&nbsp;</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
			<%
				}
			%>
			
			<%
				if ( fragment != null )
				{
					fragment.callBack( request, response );
				}
			%>
		</TABLE>
		
		<div id="reportParameters">
			<div id="outputFormat"><%= request.getParameter("outputFormat") %></div>
		</div>



<!--  <div   class="row-fluid">  -->
<!--    <div class="span12">  -->
<!--     <ul class="breadcrumb">  -->
<%--      <li><a href="../">Home</a> <span class="divider">&#62;</span></li>  --%>
<%--      <li><a href="../">Xxxxx</a> <span class="divider">&#62;</span></li>  --%>
<!--      <li class="active">Caricamento file</li>  -->
<!--     </ul>  -->
<!--    </div>  -->
<!--   </div>  -->
<!--   <div style=" min-height:550px; height:auto !important; " class="container-fluid">  -->
<!--    <div class="row-fluid">  -->
<!--     <div class="span12 ">  -->
<!--      <div class="contentPage">  -->
<!--       <form action="#" method="get">  -->
<!--        <h3>Caricamento file</h3>  -->
<!--        <div class="loading5_img"></div>  -->
<!--        <p> <a href="/siacrepapp/home.do" class="btn"   >indietro</a> </p>  -->
<!--       </form>  -->
<!--      </div>  -->
<!--     </div>  -->
<!--    </div>  -->
<!--   </div>  -->




	
	
<%-- Inclusione del footer del portale --%>








	<%@include file="../common/Locale.jsp" %>	
	<%@include file="../common/Attributes.jsp" %>	

	<script type="text/javascript">
	// <![CDATA[
		var hasSVGSupport = false;
		var useVBMethod = false;
		if ( navigator.mimeTypes != null && navigator.mimeTypes.length > 0 )
		{
		    if ( navigator.mimeTypes["image/svg+xml"] != null )
		    {
		        hasSVGSupport = true;
		    }
		}
		else
		{
		    useVBMethod = true;
		}
		
	// ]]>
	</script>
	
	<script type="text/vbscript">
		On Error Resume Next
		If useVBMethod = true Then
		    hasSVGSupport = IsObject(CreateObject("Adobe.SVGCtl"))
		End If
	</script>

	<script type="text/javascript">
		var Mask =  new Mask(false); //create mask using "div"
		var BrowserUtility = new BrowserUtility();
		DragDrop = new BirtDndManager();

		var birtToolbar = new BirtToolbar( 'toolbar' );
		var navigationBar = new BirtNavigationBar( 'navigationBar' );
		
 		var birtToc = new BirtToc( 'display0' );
		var birtProgressBar = new BirtProgressBar( 'progressBar' );
		var birtReportDocument = new BirtReportDocument( "Document", birtToc );

		var birtParameterDialog = new BirtParameterDialog( 'parameterDialog', 'frameset' );
		var birtSimpleExportDataDialog = new BirtSimpleExportDataDialog( 'simpleExportDataDialog' );
		var birtExportReportDialog = new BirtExportReportDialog( 'exportReportDialog' );
		var birtPrintReportDialog = new BirtPrintReportDialog( 'printReportDialog' );
		var birtPrintReportServerDialog = new BirtPrintReportServerDialog( 'printReportServerDialog' );
		var birtExceptionDialog = new BirtExceptionDialog( 'exceptionDialog' );
		var birtConfirmationDialog = new BirtConfirmationDialog( 'confirmationDialog' );

		// register the base elements to the mask, so their input
		// will be disabled when a dialog is popped up.
		Mask.setBaseElements( new Array( birtToolbar.__instance, navigationBar.__instance, birtReportDocument.__instance) );
		
		function init()
		{
			hideContent();
			registerCancelButton();
			
			soapURL = birtUtility.initSessionId( soapURL );
			
			birtParameterDialog.__cb_bind( );

			birtParameterDialog.__okPress = renderReport;
		}
		
		
		function renderReport()
		{ 
			var retOk = birtParameterDialog.collect_parameter();
			//birtParameterDialog.__l_hide( );		
			
			if (retOk)
			{
				var action = document.location.href.replace('/frameset\?', '/output?').replace('outputFormat', '__format');
				
			
				if (jQuery('#outputFormat').text() == 'xls')						
					action = action + '&__emitterid=org.eclipse.birt.report.engine.emitter.prototype.excel&__export_single_page=true';
					// action = action + '&__emitterid=org.uguess.birt.report.engine.emitter.xls&__export_single_page=true';
					//action = action + '&__emitterid=uk.co.spudsoft.birt.emitters.excel.XlsxEmitter';
					//action = action + '&__emitterid=it.csi.siac.siacrepapp.frontend.ui.servlet.birt.emitter.xls';
					
				
				
				birtParameterDialog.__doSubmit(action, null);
				
				
			
				showAfterOkPressedMessage();
			}
		}

		
		function hideContent()
		{
			jQuery('#reportParameters').hide();
			jQuery('#content').hide();
			jQuery('.body_caption_top').hide();
			jQuery('.body_caption').hide();
			jQuery('#toolbar').hide();
			jQuery('BODY').show();
		}
		
		
		function registerCancelButton()
		{
			jQuery('#parameterDialogcancelButton > input').click(function() 
			{  
				document.location.href = '/siacrepapp/home.do';
			});
		}
		
		// When link to internal bookmark, use javascript to fire an Ajax request
		function catchBookmark( bookmark )
		{	
			birtEventDispatcher.broadcastEvent( birtEvent.__E_GETPAGE, { name : "__bookmark", value : bookmark } );		
		}
		
		</script><div id="Mask" style="position: absolute; top: 0px; left: 0px; width: 1517px; height: 704px; z-index: 200; opacity: 0; background-color: rgb(0, 68, 255);"></div><div style="position: absolute; top: 0px; left: 0px; width: 1517px; height: 704px; z-index: 200; opacity: 0; display: none; cursor: move; background-color: rgb(255, 0, 0);"></div><iframe src="birt/pages/common/blank.html" scrolling="no" marginheight="0px" marginwidth="0px" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 262px; z-index: 300; opacity: 0; display: none; background-color: rgb(219, 228, 238);"></iframe>
		
		
		<!-- AG: funzioni aggiunte per fare apparire la modale quando il document Ã¨ pronto e lo setto -->
		<!-- **************************************************************************************** -->
		<script>
		jQuery( document ).ready(function() {

			SettParameterDialog();
			
		});

		function SettParameterDialog()
		{	
			jQuery("#ModalparameterDialog").modal({
				  "backdrop"  : "static",
				  "keyboard"  : true,
				  "show"      : true  
				});
		};
		
		// AG: funzione per chiudere la modale al conferma 
		function closeParameterDialog()
		{	
			jQuery("#ModalparameterDialog").modal("hide");
		};
		
		function showAfterOkPressedMessage()
		{	
			jQuery('#parameterDialogdialogTitleBar > div.dTitleTextContainer').hide();
			
			// SIAC-6001: mantendo il contenitore dei dati, e lo pulisco
			var parameterDialog = jQuery('#parameterDialogdialogContentContainer > div.birtviewer_parameter_dialog');
			parameterDialog.find(':input').attr('disabled', true);
			parameterDialog.prepend(jQuery('#parameterDialog_endMsgContainer').html());
			/*
			jQuery('#parameterDialogdialogContentContainer > div.birtviewer_parameter_dialog').html(
					jQuery('#parameterDialog_endMsgContainer').html());
			*/
			
			
			jQuery('#parameterDialog div.dialogBtnBar').html(
					jQuery('#parameterDialog_endMsgBackButtonContainer').html());
			
			jQuery('#parameterDialog_endMsgBackButton > input').click(function() 
			{  
				document.location.href = '/siacrepapp/home.do';
			});
		};
		
		
			
	</script>



<script type="text/javascript">
<% if(request.getParameter("hideForContentOnly") != null) { %>
jQuery(document).ready(function() 
{
	jQuery(".hideForContentOnly").hide(); 
	
});
<% } %>
</script>
	


<!--[if IE 9]><script type="text/javascript">
       
	
	jQuery( document ).ready(function() {
		jQuery("iframe#parameterDialogiframe").css("z-index", "");
	});


 </script><![endif]-->   



<div id="Mask" style="position: absolute; top: 0px; left: 0px; width: 1517px; height: 704px; z-index: 200; opacity: 0; background-color: rgb(0, 68, 255);"></div><div style="position: absolute; top: 0px; left: 0px; width: 1517px; height: 704px; z-index: 200; opacity: 0; display: none; cursor: move; background-color: rgb(255, 0, 0);"></div><iframe src="birt/pages/common/blank.html" scrolling="no" marginheight="0px" marginwidth="0px" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 752px; z-index: 300; opacity: 0; display: none; background-color: rgb(219, 228, 238);"></iframe>


<div class="modal-backdrop fade in"></div></body></html>

