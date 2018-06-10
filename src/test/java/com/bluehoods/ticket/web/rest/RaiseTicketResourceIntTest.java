package com.bluehoods.ticket.web.rest;

import com.bluehoods.ticket.TicketApp;

import com.bluehoods.ticket.domain.RaiseTicket;
import com.bluehoods.ticket.repository.RaiseTicketRepository;
import com.bluehoods.ticket.service.RaiseTicketService;
import com.bluehoods.ticket.repository.search.RaiseTicketSearchRepository;
import com.bluehoods.ticket.service.dto.RaiseTicketDTO;
import com.bluehoods.ticket.service.mapper.RaiseTicketMapper;
import com.bluehoods.ticket.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.bluehoods.ticket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bluehoods.ticket.domain.enumeration.IssueStatus;
/**
 * Test class for the RaiseTicketResource REST controller.
 *
 * @see RaiseTicketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketApp.class)
public class RaiseTicketResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final IssueStatus DEFAULT_ISSUE_STATUS = IssueStatus.NEW;
    private static final IssueStatus UPDATED_ISSUE_STATUS = IssueStatus.IN_PROGRESS;

    private static final byte[] DEFAULT_ATTACH_SCREENSHOT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACH_SCREENSHOT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACH_SCREENSHOT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RaiseTicketRepository raiseTicketRepository;

    @Autowired
    private RaiseTicketMapper raiseTicketMapper;

    @Autowired
    private RaiseTicketService raiseTicketService;

    @Autowired
    private RaiseTicketSearchRepository raiseTicketSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaiseTicketMockMvc;

    private RaiseTicket raiseTicket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RaiseTicketResource raiseTicketResource = new RaiseTicketResource(raiseTicketService);
        this.restRaiseTicketMockMvc = MockMvcBuilders.standaloneSetup(raiseTicketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RaiseTicket createEntity(EntityManager em) {
        RaiseTicket raiseTicket = new RaiseTicket()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .openDate(DEFAULT_OPEN_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .issueStatus(DEFAULT_ISSUE_STATUS)
            .attachScreenshot(DEFAULT_ATTACH_SCREENSHOT)
            .attachScreenshotContentType(DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE)
            .remarks(DEFAULT_REMARKS);
        return raiseTicket;
    }

    @Before
    public void initTest() {
        raiseTicketSearchRepository.deleteAll();
        raiseTicket = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaiseTicket() throws Exception {
        int databaseSizeBeforeCreate = raiseTicketRepository.findAll().size();

        // Create the RaiseTicket
        RaiseTicketDTO raiseTicketDTO = raiseTicketMapper.toDto(raiseTicket);
        restRaiseTicketMockMvc.perform(post("/api/raise-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raiseTicketDTO)))
            .andExpect(status().isCreated());

        // Validate the RaiseTicket in the database
        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeCreate + 1);
        RaiseTicket testRaiseTicket = raiseTicketList.get(raiseTicketList.size() - 1);
        assertThat(testRaiseTicket.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRaiseTicket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRaiseTicket.getOpenDate()).isEqualTo(DEFAULT_OPEN_DATE);
        assertThat(testRaiseTicket.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testRaiseTicket.getIssueStatus()).isEqualTo(DEFAULT_ISSUE_STATUS);
        assertThat(testRaiseTicket.getAttachScreenshot()).isEqualTo(DEFAULT_ATTACH_SCREENSHOT);
        assertThat(testRaiseTicket.getAttachScreenshotContentType()).isEqualTo(DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE);
        assertThat(testRaiseTicket.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the RaiseTicket in Elasticsearch
        RaiseTicket raiseTicketEs = raiseTicketSearchRepository.findOne(testRaiseTicket.getId());
        assertThat(raiseTicketEs).isEqualToIgnoringGivenFields(testRaiseTicket);
    }

    @Test
    @Transactional
    public void createRaiseTicketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raiseTicketRepository.findAll().size();

        // Create the RaiseTicket with an existing ID
        raiseTicket.setId(1L);
        RaiseTicketDTO raiseTicketDTO = raiseTicketMapper.toDto(raiseTicket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaiseTicketMockMvc.perform(post("/api/raise-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raiseTicketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RaiseTicket in the database
        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = raiseTicketRepository.findAll().size();
        // set the field null
        raiseTicket.setTitle(null);

        // Create the RaiseTicket, which fails.
        RaiseTicketDTO raiseTicketDTO = raiseTicketMapper.toDto(raiseTicket);

        restRaiseTicketMockMvc.perform(post("/api/raise-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raiseTicketDTO)))
            .andExpect(status().isBadRequest());

        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRaiseTickets() throws Exception {
        // Initialize the database
        raiseTicketRepository.saveAndFlush(raiseTicket);

        // Get all the raiseTicketList
        restRaiseTicketMockMvc.perform(get("/api/raise-tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raiseTicket.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].openDate").value(hasItem(DEFAULT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].issueStatus").value(hasItem(DEFAULT_ISSUE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].attachScreenshotContentType").value(hasItem(DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachScreenshot").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_SCREENSHOT))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getRaiseTicket() throws Exception {
        // Initialize the database
        raiseTicketRepository.saveAndFlush(raiseTicket);

        // Get the raiseTicket
        restRaiseTicketMockMvc.perform(get("/api/raise-tickets/{id}", raiseTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raiseTicket.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.openDate").value(DEFAULT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.issueStatus").value(DEFAULT_ISSUE_STATUS.toString()))
            .andExpect(jsonPath("$.attachScreenshotContentType").value(DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachScreenshot").value(Base64Utils.encodeToString(DEFAULT_ATTACH_SCREENSHOT)))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaiseTicket() throws Exception {
        // Get the raiseTicket
        restRaiseTicketMockMvc.perform(get("/api/raise-tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaiseTicket() throws Exception {
        // Initialize the database
        raiseTicketRepository.saveAndFlush(raiseTicket);
        raiseTicketSearchRepository.save(raiseTicket);
        int databaseSizeBeforeUpdate = raiseTicketRepository.findAll().size();

        // Update the raiseTicket
        RaiseTicket updatedRaiseTicket = raiseTicketRepository.findOne(raiseTicket.getId());
        // Disconnect from session so that the updates on updatedRaiseTicket are not directly saved in db
        em.detach(updatedRaiseTicket);
        updatedRaiseTicket
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .openDate(UPDATED_OPEN_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .issueStatus(UPDATED_ISSUE_STATUS)
            .attachScreenshot(UPDATED_ATTACH_SCREENSHOT)
            .attachScreenshotContentType(UPDATED_ATTACH_SCREENSHOT_CONTENT_TYPE)
            .remarks(UPDATED_REMARKS);
        RaiseTicketDTO raiseTicketDTO = raiseTicketMapper.toDto(updatedRaiseTicket);

        restRaiseTicketMockMvc.perform(put("/api/raise-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raiseTicketDTO)))
            .andExpect(status().isOk());

        // Validate the RaiseTicket in the database
        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeUpdate);
        RaiseTicket testRaiseTicket = raiseTicketList.get(raiseTicketList.size() - 1);
        assertThat(testRaiseTicket.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRaiseTicket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRaiseTicket.getOpenDate()).isEqualTo(UPDATED_OPEN_DATE);
        assertThat(testRaiseTicket.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testRaiseTicket.getIssueStatus()).isEqualTo(UPDATED_ISSUE_STATUS);
        assertThat(testRaiseTicket.getAttachScreenshot()).isEqualTo(UPDATED_ATTACH_SCREENSHOT);
        assertThat(testRaiseTicket.getAttachScreenshotContentType()).isEqualTo(UPDATED_ATTACH_SCREENSHOT_CONTENT_TYPE);
        assertThat(testRaiseTicket.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the RaiseTicket in Elasticsearch
        RaiseTicket raiseTicketEs = raiseTicketSearchRepository.findOne(testRaiseTicket.getId());
        assertThat(raiseTicketEs).isEqualToIgnoringGivenFields(testRaiseTicket);
    }

    @Test
    @Transactional
    public void updateNonExistingRaiseTicket() throws Exception {
        int databaseSizeBeforeUpdate = raiseTicketRepository.findAll().size();

        // Create the RaiseTicket
        RaiseTicketDTO raiseTicketDTO = raiseTicketMapper.toDto(raiseTicket);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaiseTicketMockMvc.perform(put("/api/raise-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raiseTicketDTO)))
            .andExpect(status().isCreated());

        // Validate the RaiseTicket in the database
        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaiseTicket() throws Exception {
        // Initialize the database
        raiseTicketRepository.saveAndFlush(raiseTicket);
        raiseTicketSearchRepository.save(raiseTicket);
        int databaseSizeBeforeDelete = raiseTicketRepository.findAll().size();

        // Get the raiseTicket
        restRaiseTicketMockMvc.perform(delete("/api/raise-tickets/{id}", raiseTicket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean raiseTicketExistsInEs = raiseTicketSearchRepository.exists(raiseTicket.getId());
        assertThat(raiseTicketExistsInEs).isFalse();

        // Validate the database is empty
        List<RaiseTicket> raiseTicketList = raiseTicketRepository.findAll();
        assertThat(raiseTicketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRaiseTicket() throws Exception {
        // Initialize the database
        raiseTicketRepository.saveAndFlush(raiseTicket);
        raiseTicketSearchRepository.save(raiseTicket);

        // Search the raiseTicket
        restRaiseTicketMockMvc.perform(get("/api/_search/raise-tickets?query=id:" + raiseTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raiseTicket.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].openDate").value(hasItem(DEFAULT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].issueStatus").value(hasItem(DEFAULT_ISSUE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].attachScreenshotContentType").value(hasItem(DEFAULT_ATTACH_SCREENSHOT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachScreenshot").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_SCREENSHOT))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaiseTicket.class);
        RaiseTicket raiseTicket1 = new RaiseTicket();
        raiseTicket1.setId(1L);
        RaiseTicket raiseTicket2 = new RaiseTicket();
        raiseTicket2.setId(raiseTicket1.getId());
        assertThat(raiseTicket1).isEqualTo(raiseTicket2);
        raiseTicket2.setId(2L);
        assertThat(raiseTicket1).isNotEqualTo(raiseTicket2);
        raiseTicket1.setId(null);
        assertThat(raiseTicket1).isNotEqualTo(raiseTicket2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaiseTicketDTO.class);
        RaiseTicketDTO raiseTicketDTO1 = new RaiseTicketDTO();
        raiseTicketDTO1.setId(1L);
        RaiseTicketDTO raiseTicketDTO2 = new RaiseTicketDTO();
        assertThat(raiseTicketDTO1).isNotEqualTo(raiseTicketDTO2);
        raiseTicketDTO2.setId(raiseTicketDTO1.getId());
        assertThat(raiseTicketDTO1).isEqualTo(raiseTicketDTO2);
        raiseTicketDTO2.setId(2L);
        assertThat(raiseTicketDTO1).isNotEqualTo(raiseTicketDTO2);
        raiseTicketDTO1.setId(null);
        assertThat(raiseTicketDTO1).isNotEqualTo(raiseTicketDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(raiseTicketMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(raiseTicketMapper.fromId(null)).isNull();
    }
}
