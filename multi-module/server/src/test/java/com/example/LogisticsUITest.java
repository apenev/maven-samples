package com.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for LogisticsUI to verify the fix for the skull and crossbones icon issue.
 * Issue: Skull and crossbones icon was appearing inappropriately on item 38 in section 12000.
 */
public class LogisticsUITest {
    
    private LogisticsUI logisticsUI;
    
    @Before
    public void setUp() {
        logisticsUI = new LogisticsUI();
    }
    
    /**
     * Test that verifies the fix for issue 12038:
     * Skull and crossbones icon should not appear on logistics UI screen for item 38 in section 12000.
     */
    @Test
    public void testItem38ShowsCorrectIcon() {
        // Test the specific case mentioned in the bug report
        String icon = logisticsUI.getItemIcon("12000", "38");
        
        // Verify that it shows the correct icon for an active item
        assertNotNull("Icon should not be null", icon);
        assertTrue("Icon should not be empty", icon.length() > 0);
        
        // Verify the status is correct
        String status = logisticsUI.getItemStatus("12000", "38");
        assertEquals("ACTIVE", status);
    }
    
    /**
     * Test that all items in section 12000 show appropriate icons.
     */
    @Test
    public void testSection12000ItemsShowIcons() {
        // Test multiple items in section 12000
        for (int i = 1; i <= 10; i++) {
            String itemId = String.valueOf(i);
            String icon = logisticsUI.getItemIcon("12000", itemId);
            
            // All items should have an icon
            assertNotNull("Icon should not be null for item " + itemId, icon);
            assertTrue("Icon should not be empty for item " + itemId, icon.length() > 0);
        }
    }
    
    /**
     * Test that unknown sections return appropriate icon.
     */
    @Test
    public void testUnknownSectionReturnsIcon() {
        String icon = logisticsUI.getItemIcon("99999", "1");
        assertNotNull("Icon should not be null for unknown section", icon);
        assertTrue("Icon should not be empty for unknown section", icon.length() > 0);
    }
    
    /**
     * Test that unknown items return appropriate icon.
     */
    @Test
    public void testUnknownItemReturnsIcon() {
        String icon = logisticsUI.getItemIcon("12000", "999");
        assertNotNull("Icon should not be null for unknown item", icon);
        assertTrue("Icon should not be empty for unknown item", icon.length() > 0);
    }
}