package com.bluehoods.ticket.web.rest;

import com.bluehoods.ticket.TicketApp;

import com.bluehoods.ticket.domain.Assigs;
import com.bluehoods.ticket.repository.AssigsRepository;
import com.bluehoods.ticket.service.AssigsService;
import com.bluehoods.ticket.repository.search.AssigsSearchRepository;
import com.bluehoods.ticket.service.dto.AssigsDTO;
import com.bluehoods.ticket.service.mapper.AssigsMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static com.bluehoods.ticket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssigsResource REST controller.
 *
 * @see AssigsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketApp.class)
public class AssigsResourceIntTest {

    private static final Long DEFAULT_RESOURCE_ID = 1L;
    private static final Long UPDATED_RESOURCE_ID = 2L;

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;

    private static final Long DEFAULT_EFFORT = 1L;
    private static final Long UPDATED_EFFORT = 2L;

    @Autowired
    private AssigsRepository assigsRepository;

    @Autowired
    private AssigsMapper assigsMapper;

    @Autowired
    private AssigsService assigsService;

    @Autowired
    private AssigsSearchRepository assigsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssigsMockMvc;

    private Assigs assigs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssigsResource assigsResource = new AssigsResource(assigsService);
        this.restAssigsMockMvc = MockMvcBuilders.standaloneSetup(assigsResource)
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
    public static Assigs createEntity(EntityManager em) {
        Assigs assigs = new Assigs()
            .resourceId(DEFAULT_RESOURCE_ID)
            .roleId(DEFAULT_ROLE_ID)
            .taskId(DEFAULT_TASK_ID)
            .effort(DEFAULT_EFFORT);
        return assigs;
    }

    @Before
    public void initTest() {
        assigsSearchRepository.deleteAll();
        assigs = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssigs() throws Exception {
        int databaseSizeBeforeCreate = assigsRepository.findAll().size();

        // Create the Assigs
        AssigsDTO assigsDTO = assigsMapper.toDto(assigs);
        restAssigsMockMvc.perform(post("/api/assigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assigsDTO)))
            .andExpect(status().isCreated());

        // Validate the Assigs in the database
        List<Assigs> assigsList = assigsRepository.findAll();
        assertThat(assigsList).hasSize(databaseSizeBeforeCreate + 1);
        Assigs testAssigs = assigsList.get(assigsList.size() - 1);
        assertThat(testAssigs.getResourceId()).isEqualTo(DEFAULT_RESOURCE_ID);
        assertThat(testAssigs.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testAssigs.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testAssigs.getEffort()).isEqualTo(DEFAULT_EFFORT);

        // Validate the Assigs in Elasticsearch
        Assigs assigsEs = assigsSearchRepository.findOne(testAssigs.getId());
        assertThat(assigsEs).isEqualToIgnoringGivenFields(testAssigs);
    }

    @Test
    @Transactional
    public void createAssigsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assigsRepository.findAll().size();

        // Create the Assigs with an existing ID
        assigs.setId(1L);
        AssigsDTO assigsDTO = assigsMapper.toDto(assigs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssigsMockMvc.perform(post("/api/assigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assigsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assigs in the database
        List<Assigs> assigsList = assigsRepository.findAll();
        assertThat(assigsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssigs() throws Exception {
        // Initialize the database
        assigsRepository.saveAndFlush(assigs);

        // Get all the assigsList
        restAssigsMockMvc.perform(get("/api/assigs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assigs.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].effort").value(hasItem(DEFAULT_EFFORT.intValue())));
    }

    @Test
    @Transactional
    public void getAssigs() throws Exception {
        // Initialize the database
        assigsRepository.saveAndFlush(assigs);

        // Get the assigs
        restAssigsMockMvc.perform(get("/api/assigs/{id}", assigs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assigs.getId().intValue()))
            .andExpect(jsonPath("$.resourceId").value(DEFAULT_RESOURCE_ID.intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.effort").value(DEFAULT_EFFORT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssigs() throws Exception {
        // Get the assigs
        restAssigsMockMvc.perform(get("/api/assigs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssigs() throws Exception {
        // Initialize the database
        assigsRepository.saveAndFlush(assigs);
        assigsSearchRepository.save(assigs);
        int databaseSizeBeforeUpdate = assigsRepository.findAll().size();

        // Update the assigs
        Assigs updatedAssigs = assigsRepository.findOne(assigs.getId());
        // Disconnect from session so that the updates on updatedAssigs are not directly saved in db
        em.detach(updatedAssigs);
        updatedAssigs
            .resourceId(UPDATED_RESOURCE_ID)
            .roleId(UPDATED_ROLE_ID)
            .taskId(UPDATED_TASK_ID)
            .effort(UPDATED_EFFORT);
        AssigsDTO assigsDTO = assigsMapper.toDto(updatedAssigs);

        restAssigsMockMvc.perform(put("/api/assigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assigsDTO)))
            .andExpect(status().isOk());

        // Validate the Assigs in the database
        List<Assigs> assigsList = assigsRepository.findAll();
        assertThat(assigsList).hasSize(databaseSizeBeforeUpdate);
        Assigs testAssigs = assigsList.get(assigsList.size() - 1);
        assertThat(testAssigs.getResourceId()).isEqualTo(UPDATED_RESOURCE_ID);
        assertThat(testAssigs.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testAssigs.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testAssigs.getEffort()).isEqualTo(UPDATED_EFFORT);

        // Validate the Assigs in Elasticsearch
        Assigs assigsEs = assigsSearchRepository.findOne(testAssigs.getId());
        assertThat(assigsEs).isEqualToIgnoringGivenFields(testAssigs);
    }

    @Test
    @Transactional
    public void updateNonExistingAssigs() throws Exception {
        int databaseSizeBeforeUpdate = assigsRepository.findAll().size();

        // Create the Assigs
        AssigsDTO assigsDTO = assigsMapper.toDto(assigs);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssigsMockMvc.perform(put("/api/assigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assigsDTO)))
            .andExpect(status().isCreated());

        // Validate the Assigs in the database
        List<Assigs> assigsList = assigsRepository.findAll();
        assertThat(assigsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssigs() throws Exception {
        // Initialize the database
        assigsRepository.saveAndFlush(assigs);
        assigsSearchRepository.save(assigs);
        int databaseSizeBeforeDelete = assigsRepository.findAll().size();

        // Get the assigs
        restAssigsMockMvc.perform(delete("/api/assigs/{id}", assigs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assigsExistsInEs = assigsSearchRepository.exists(assigs.getId());
        assertThat(assigsExistsInEs).isFalse();

        // Validate the database is empty
        List<Assigs> assigsList = assigsRepository.findAll();
        assertThat(assigsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssigs() throws Exception {
        // Initialize the database
        assigsRepository.saveAndFlush(assigs);
        assigsSearchRepository.save(assigs);

        // Search the assigs
        restAssigsMockMvc.perform(get("/api/_search/assigs?query=id:" + assigs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assigs.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].effort").value(hasItem(DEFAULT_EFFORT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assigs.class);
        Assigs assigs1 = new Assigs();
        assigs1.setId(1L);
        Assigs assigs2 = new Assigs();
        assigs2.setId(assigs1.getId());
        assertThat(assigs1).isEqualTo(assigs2);
        assigs2.setId(2L);
        assertThat(assigs1).isNotEqualTo(assigs2);
        assigs1.setId(null);
        assertThat(assigs1).isNotEqualTo(assigs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssigsDTO.class);
        AssigsDTO assigsDTO1 = new AssigsDTO();
        assigsDTO1.setId(1L);
        AssigsDTO assigsDTO2 = new AssigsDTO();
        assertThat(assigsDTO1).isNotEqualTo(assigsDTO2);
        assigsDTO2.setId(assigsDTO1.getId());
        assertThat(assigsDTO1).isEqualTo(assigsDTO2);
        assigsDTO2.setId(2L);
        assertThat(assigsDTO1).isNotEqualTo(assigsDTO2);
        assigsDTO1.setId(null);
        assertThat(assigsDTO1).isNotEqualTo(assigsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assigsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assigsMapper.fromId(null)).isNull();
    }
}
