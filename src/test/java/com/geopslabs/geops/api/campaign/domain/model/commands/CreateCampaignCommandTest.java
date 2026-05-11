package com.geopslabs.geops.api.campaign.domain.model.commands;

import com.geopslabs.geops.api.shared.domain.model.valueobjects.ERole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateCampaignCommandTest {

    private static final ZoneId LIMA = ZoneId.of("America/Lima");
    private static final LocalDate TOMORROW   = LocalDate.now(LIMA).plusDays(1);
    private static final LocalDate NEXT_WEEK  = LocalDate.now(LIMA).plusDays(7);
    private static final LocalDate YESTERDAY  = LocalDate.now(LIMA).minusDays(1);

    private CreateCampaignCommand valid() {
        return new CreateCampaignCommand(1L, ERole.OWNER, "My Campaign",
                "Description", TOMORROW, NEXT_WEEK, 1000f);
    }

    @Test
    void constructor_WithValidData_CreatesCommand() {
        var cmd = valid();
        assertThat(cmd.name()).isEqualTo("My Campaign");
        assertThat(cmd.userId()).isEqualTo(1L);
        assertThat(cmd.requesterRole()).isEqualTo(ERole.OWNER);
    }

    @Test
    void constructor_WithNullName_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(1L, ERole.OWNER, null, "Desc", TOMORROW, NEXT_WEEK, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void constructor_WithBlankName_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(1L, ERole.OWNER, "  ", "Desc", TOMORROW, NEXT_WEEK, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void constructor_WithPastStartDate_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(1L, ERole.OWNER, "Camp", "Desc", YESTERDAY, NEXT_WEEK, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("future");
    }

    @Test
    void constructor_WithEndDateBeforeStartDate_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(1L, ERole.OWNER, "Camp", "Desc", NEXT_WEEK, TOMORROW, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("End date");
    }

    @Test
    void constructor_WithBudgetExceedingMax_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(1L, ERole.OWNER, "Camp", "Desc", TOMORROW, NEXT_WEEK, 2_000_000f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("budget");
    }

    @Test
    void constructor_WithNullUserId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() ->
                new CreateCampaignCommand(null, ERole.OWNER, "Camp", "Desc", TOMORROW, NEXT_WEEK, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("userId");
    }

    @Test
    void constructor_WithXssInName_SanitizesName() {
        var cmd = new CreateCampaignCommand(1L, ERole.OWNER,
                "<script>alert('xss')</script>Clean Name",
                "Desc", TOMORROW, NEXT_WEEK, null);
        assertThat(cmd.name()).doesNotContain("<script>").contains("Clean Name");
    }

    @Test
    void constructor_WithXssInDescription_SanitizesDescription() {
        var cmd = new CreateCampaignCommand(1L, ERole.OWNER, "Name",
                "<b>Bold</b> description", TOMORROW, NEXT_WEEK, null);
        assertThat(cmd.description()).doesNotContain("<b>").contains("Bold");
    }
}
