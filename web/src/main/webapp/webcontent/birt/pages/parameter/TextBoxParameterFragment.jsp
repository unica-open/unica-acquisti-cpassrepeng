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
<%@ page import="org.eclipse.birt.report.utility.ParameterAccessor,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.context.ScalarParameterBean" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%-----------------------------------------------------------------------------
	Text box parameter control
-----------------------------------------------------------------------------%>
<%
	ScalarParameterBean parameterBean = ( ScalarParameterBean ) attributeBean.getParameterBean( );
	String encodedParameterName = ParameterAccessor.htmlEncode( parameterBean.getName( ) );
%>
<TR>
	<TD NOWRAP>
		<LABEL FOR="<%= encodedParameterName %>"><%= parameterBean.getDisplayName( ) %>
		<%-- is required --%>
		<%
		if ( parameterBean.isRequired( ) )
		{
		%>
			<FONT COLOR="red">*</FONT>
		<%
		}
		%>
		</LABEL>
	</TD>


	<TD NOWRAP>
	<INPUT TYPE="HIDDEN" ID="control_type" VALUE="text">
	<INPUT TYPE="HIDDEN" ID="data_type" VALUE="<%="" + parameterBean.getParameter( ).getDataType( ) %>">

		<INPUT CLASS="BirtViewer_parameter_dialog_Input"
			TYPE="<%= parameterBean.isValueConcealed( )? "PASSWORD" : "TEXT" %>"
			NAME="<%= encodedParameterName %>"
			ID="<%= encodedParameterName %>" 
			TITLE="<%= parameterBean.getToolTip( ) %>"
			VALUE="<%= ParameterAccessor.htmlEncode( ( parameterBean.getDisplayText( ) == null )? "" : parameterBean.getDisplayText( ) ) %>" 
			<%= ( !parameterBean.isRequired( ) && parameterBean.getValue( ) == null )? "DISABLED='true'" : "" %>
			<%= parameterBean.isRequired( ) ? "aria-required='true'" : "" %>
            >

<%
	if ( !parameterBean.isRequired( ) )
	{
%>
							
		<LABEL FOR="<%= encodedParameterName + "_radio_notnull" %>" CLASS="birtviewer_hidden_label">Input text</LABEL>	
		<INPUT TYPE="RADIO"
			onclick="jQuery(this).siblings('input.BirtViewer_parameter_dialog_Input').prop('disabled', false)"
			ID="<%= encodedParameterName + "_radio_notnull" %>"
			VALUE="<%= encodedParameterName %>"
			<%= (parameterBean.getValue( ) != null)? "CHECKED" : "" %>>
			
				<LABEL FOR="<%= encodedParameterName + "_radio_null" %>" CLASS="birtviewer_hidden_label">Nessun valore</LABEL>	
		 Nessun valore <INPUT TYPE="RADIO"
			onclick="jQuery(this).siblings('input.BirtViewer_parameter_dialog_Input').prop('disabled', true)"
			ID="<%= encodedParameterName + "_radio_null"%>"
			VALUE="<%= encodedParameterName %>"
			<%= ( parameterBean.getValue( ) == null )? "CHECKED" : "" %>>
			
<%
	}
%>	


		<INPUT TYPE="HIDDEN"
			ID="<%= encodedParameterName + "_value" %>"
			VALUE="<%= ParameterAccessor.htmlEncode( ( parameterBean.getValue( ) == null )? "" : parameterBean.getValue( ) ) %>"
			>
		
		<INPUT TYPE="HIDDEN"
			ID="<%= encodedParameterName + "_displayText" %>"
			VALUE="<%= ParameterAccessor.htmlEncode( ( parameterBean.getDisplayText( ) == null )? "" : parameterBean.getDisplayText( ) ) %>"
			>




		<INPUT TYPE="HIDDEN" ID="isRequired" 
			VALUE = "<%= parameterBean.isRequired( )? "true": "false" %>">
	</TD>
</TR>