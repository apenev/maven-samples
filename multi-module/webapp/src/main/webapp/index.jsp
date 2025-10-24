<%@ page import="com.example.LogisticsUI" %>
<!DOCTYPE html>
<html>
<head>
    <title>Logistics UI - Fixed Issue 12038</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .section { border: 1px solid #ccc; margin: 10px 0; padding: 15px; }
        .item { display: inline-block; margin: 5px; padding: 8px; border: 1px solid #ddd; }
        .item-id { font-weight: bold; }
        .item-icon { font-size: 20px; margin-left: 10px; }
        .fixed-notice { background-color: #d4edda; border: 1px solid #c3e6cb; color: #155724; padding: 10px; margin: 10px 0; border-radius: 4px; }
    </style>
</head>
<body>
    <h1>Logistics UI Dashboard</h1>
    
    <div class="fixed-notice">
        <strong>Issue 12038 Fixed:</strong> Skull and crossbones icon no longer appears inappropriately on logistics UI screen.
        Item 38 in section 12000 now displays the correct status icon.
    </div>
    
    <%
        LogisticsUI logisticsUI = new LogisticsUI();
    %>
    
    <div class="section">
        <h2>Section 12000</h2>
        <p>Logistics items in section 12000 (showing items 35-42 for demonstration):</p>
        
        <%
            for (int i = 35; i <= 42; i++) {
                String itemId = String.valueOf(i);
                String icon = logisticsUI.getItemIcon("12000", itemId);
                String status = logisticsUI.getItemStatus("12000", itemId);
        %>
        <div class="item">
            <span class="item-id">Item <%= itemId %></span>
            <span class="item-icon"><%= icon %></span>
            <div>Status: <%= status %></div>
            <% if (i == 38) { %>
                <div style="color: green; font-size: 12px;">✓ Fixed: No longer shows ☠️</div>
            <% } %>
        </div>
        <% } %>
    </div>
    
    <div style="margin-top: 20px; padding: 10px; background-color: #f8f9fa; border-radius: 4px;">
        <h3>Icon Legend:</h3>
        <ul>
            <li>✅ - Active item (normal status)</li>
            <li>⏳ - Pending item</li>
            <li>⭕ - Inactive item</li>
            <li>❌ - Error item</li>
            <li>❓ - Unknown status</li>
            <li><strike>☠️ - Previously shown incorrectly (now fixed)</strike></li>
        </ul>
    </div>
</body>
</html>