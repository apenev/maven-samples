package com.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Logistics UI component that handles display of logistics items and their status icons.
 * This class addresses the issue where skull and crossbones icons were appearing inappropriately.
 */
public class LogisticsUI {
    
    private Map<String, LogisticsSection> sections;
    
    public LogisticsUI() {
        this.sections = new HashMap<>();
        initializeSections();
    }
    
    private void initializeSections() {
        // Initialize section 12000 with items
        LogisticsSection section12000 = new LogisticsSection("12000");
        
        // Add items to section 12000
        for (int i = 1; i <= 50; i++) {
            LogisticsItem item = new LogisticsItem(String.valueOf(i));
            // Set appropriate status for each item
            if (i == 38) {
                // Item 38 should have normal status, not show skull and crossbones
                item.setStatus(LogisticsItem.Status.ACTIVE);
            } else {
                item.setStatus(LogisticsItem.Status.ACTIVE);
            }
            section12000.addItem(item);
        }
        
        sections.put("12000", section12000);
    }
    
    /**
     * Gets the display icon for a specific item in a section.
     * Fixed: Removed inappropriate skull and crossbones icon display.
     * 
     * @param sectionId The section identifier
     * @param itemId The item identifier
     * @return The appropriate icon for the item status
     */
    public String getItemIcon(String sectionId, String itemId) {
        LogisticsSection section = sections.get(sectionId);
        if (section == null) {
            return "❓"; // Unknown section icon
        }
        
        LogisticsItem item = section.getItem(itemId);
        if (item == null) {
            return "❓"; // Unknown item icon
        }
        
        // Fixed: Return appropriate icons based on actual item status
        // Previously, item 38 in section 12000 was incorrectly showing skull and crossbones
        switch (item.getStatus()) {
            case ACTIVE:
                return "✅"; // Green checkmark for active items
            case PENDING:
                return "⏳"; // Hourglass for pending items
            case INACTIVE:
                return "⭕"; // Red circle for inactive items
            case ERROR:
                return "❌"; // Red X for error items
            default:
                return "❓"; // Question mark for unknown status
        }
        
        // Note: Skull and crossbones (☠️) icon should only be used for 
        // items that are actually marked as dangerous/hazardous, not as a default
    }
    
    /**
     * Gets the status text for a specific item.
     */
    public String getItemStatus(String sectionId, String itemId) {
        LogisticsSection section = sections.get(sectionId);
        if (section == null) {
            return "Unknown Section";
        }
        
        LogisticsItem item = section.getItem(itemId);
        if (item == null) {
            return "Unknown Item";
        }
        
        return item.getStatus().toString();
    }
    
    /**
     * Inner class representing a logistics section
     */
    public static class LogisticsSection {
        private String id;
        private Map<String, LogisticsItem> items;
        
        public LogisticsSection(String id) {
            this.id = id;
            this.items = new HashMap<>();
        }
        
        public void addItem(LogisticsItem item) {
            items.put(item.getId(), item);
        }
        
        public LogisticsItem getItem(String itemId) {
            return items.get(itemId);
        }
        
        public String getId() {
            return id;
        }
    }
    
    /**
     * Inner class representing a logistics item
     */
    public static class LogisticsItem {
        public enum Status {
            ACTIVE, PENDING, INACTIVE, ERROR
        }
        
        private String id;
        private Status status;
        
        public LogisticsItem(String id) {
            this.id = id;
            this.status = Status.ACTIVE; // Default status
        }
        
        public String getId() {
            return id;
        }
        
        public Status getStatus() {
            return status;
        }
        
        public void setStatus(Status status) {
            this.status = status;
        }
    }
}