<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
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
				 org.eclipse.birt.report.IBirtConstants,
				 org.eclipse.birt.report.utility.ParameterAccessor,
				 org.eclipse.birt.report.resource.BirtResources" %>


<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%-----------------------------------------------------------------------------
	Dialog container fragment, shared by all standard dialogs.
-----------------------------------------------------------------------------%>

<% if ( fragment.getClientId( ).equals("parameterDialog") ) { %>
<div id="Modal<%= fragment.getClientId( ) %>" class="modal hide fade in" tabindex="-1" role="dialog" 
	aria-labelledby="Modal<%= fragment.getClientId( ) %>Label" aria-hidden="false" style="display: block;">
				<% } %>


<div id="<%= fragment.getClientId( ) %>" class="dialogBorder" style="display: none; position: relative; z-index: 201; top: 137px; left: 497.5px; width: 520px;">
	<iframe id="<%= fragment.getClientId( ) %>iframe"  name="<%= fragment.getClientId( ) %>iframe" style="z-index: 100; display: 
	none; left: 0px; top: 0px; opacity: 0; position: absolute; width: 522px; height: 429px;
	background-color: rgb(255, 0, 0);"
	 frameBorder="0" scrolling="no" src="birt/pages/common/blank.html">
	</iframe>	
	<div id="<%= fragment.getClientId( ) %>dialogTitleBar" class="dialogTitleBar dTitleBar">
		<div class="dTitleTextContainer">
			<h4 class="dialogTitleText dTitleText"><%= fragment.getTitle( ) %></h4>
		</div>
		

		<div class="dialogCloseBtnContainer dCloseBtnContainer">
			<button id="<%= fragment.getClientId( ) %>dialogCloseBtn" class="dialogCloseBtn dCloseBtn pull-right close" data-dismiss="modal" aria-hidden="true" >
						&times;</button>
		</div>
	</div>
	<!-- overflow is set as workaround for Mozilla bug https://bugzilla.mozilla.org/show_bug.cgi?id=167801 -->		
	<div  class="dialogBackground" > 
		<div class="dBackground">
			<div class="dialogContentContainer" id="<%= fragment.getClientId( ) %>dialogContentContainer">
				<%
					if ( fragment != null )
					{
						fragment.callBack( request, response );
					}
				%>
			</div>
			<div class="dialogBtnBarContainer">
				<div>
					<div class="dBtnBarDividerTop">
					</div>
					<div class="dBtnBarDividerBottom">
					</div>
				</div>
				<div class="dialogBtnBar"> 
					<div id="<%= fragment.getClientId( ) %>dialogCustomButtonContainer" class="dialogBtnBarButtonContainer">
						<div class="hideForContentOnly" id="<%= fragment.getClientId( ) %>cancelButton">
							<div class="dialogBtnBarButtonLeftBackgroundEnabled"></div>
							<div class="dialogBtnBarButtonRightBackgroundEnabled"></div>
							<input type="button" value="<%= BirtResources.getHtmlMessage( "birt.viewer.dialog.cancel" )%>" 
								title="<%= BirtResources.getHtmlMessage( "birt.viewer.dialog.cancel" )%>"  
								class="btn btn-secondary dialogBtnBarButtonText dialogBtnBarButtonEnabled"/>
						</div> 						
						<div class="dialogBtnBarDivider"></div>
						<div id="<%= fragment.getClientId( ) %>okButton">
							<div id="<%= fragment.getClientId( ) %>okButtonLeft" class="dialogBtnBarButtonLeftBackgroundEnabled"></div>
							<div id="<%= fragment.getClientId( ) %>okButtonRight" class="dialogBtnBarButtonRightBackgroundEnabled"></div>
							<input type="button" value="<%= BirtResources.getHtmlMessage( "birt.viewer.dialog.ok" ) %>" 
								title="<%= BirtResources.getHtmlMessage( "birt.viewer.dialog.ok" ) %>"  
								class="btn btn-primary dialogBtnBarButtonText dialogBtnBarButtonEnabled"/>
						</div>

					</div>							
				</div>
			</div>
		</div>
	</div>
</div>




<!-- AG: aggiunta il clear e la chiusura del div della modale -->
<div class="clear"></div>

<% if ( fragment.getClientId( ).equals("parameterDialog") ) { %>
</div>



<div class="nascosto">



<div id="<%= fragment.getClientId( ) %>_endMsgContainer">
	
	<font size="+1">
	Il report &egrave; in corso di elaborazione. Il download verr&agrave; avviato automaticamente dal sistema.
	Attendere che il download sia completato e successivamente premere Ok.</font>
	</div>



<div class="dialogBtnBar">	
<div id="<%= fragment.getClientId( ) %>_endMsgBackButtonContainer" class="dialogBtnBarButtonContainer">
	<div class="hideForContentOnly" id="<%= fragment.getClientId( ) %>_endMsgBackButton">
		<div class="dialogBtnBarButtonLeftBackgroundEnabled"></div>
		<div class="dialogBtnBarButtonRightBackgroundEnabled"></div>
		<input type="button" value="ok" 
			title="ok"  
			class="btn btn-primary dialogBtnBarButtonText dialogBtnBarButtonEnabled"/>
			
			<% if ("xbrl".equals(request.getParameter("outputFormat")) ) { %>
			<a class="btn btn-primary " href="/siacrepapp/gestioneXbrlFile.do">gestione xbrl</a>
			<% } %>
			
			
			
	</div> 						
</div>	
</div>						








</div>





<% } %>





