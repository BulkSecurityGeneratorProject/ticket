package com.bluehoods.ticket.web.rest;

import com.bluehoods.ticket.TicketApp;

import com.bluehoods.ticket.domain.File;
import com.bluehoods.ticket.repository.FileRepository;
import com.bluehoods.ticket.service.FileService;
import com.bluehoods.ticket.repository.search.FileSearchRepository;
import com.bluehoods.ticket.service.dto.FileDTO;
import com.bluehoods.ticket.service.mapper.FileMapper;
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
import java.util.List;

import static com.bluehoods.ticket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FileResource REST controller.
 *
 * @see FileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketApp.class)
public class FileResourceIntTest {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ONE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ONE_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_ONE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_ONE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_ONE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_ONE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DOCUMENT_TWO_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TWO_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_TWO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_TWO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_TWO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_TWO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DOCUMENT_THREE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_THREE_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_THREE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_THREE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_THREE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_THREE_CONTENT_TYPE = "image/png";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileSearchRepository fileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileMockMvc;

    private File file;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileResource fileResource = new FileResource(fileService);
        this.restFileMockMvc = MockMvcBuilders.standaloneSetup(fileResource)
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
    public static File createEntity(EntityManager em) {
        File file = new File()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentOneDetails(DEFAULT_DOCUMENT_ONE_DETAILS)
            .documentOne(DEFAULT_DOCUMENT_ONE)
            .documentOneContentType(DEFAULT_DOCUMENT_ONE_CONTENT_TYPE)
            .documentTwoDetails(DEFAULT_DOCUMENT_TWO_DETAILS)
            .documentTwo(DEFAULT_DOCUMENT_TWO)
            .documentTwoContentType(DEFAULT_DOCUMENT_TWO_CONTENT_TYPE)
            .documentThreeDetails(DEFAULT_DOCUMENT_THREE_DETAILS)
            .documentThree(DEFAULT_DOCUMENT_THREE)
            .documentThreeContentType(DEFAULT_DOCUMENT_THREE_CONTENT_TYPE);
        return file;
    }

    @Before
    public void initTest() {
        fileSearchRepository.deleteAll();
        file = createEntity(em);
    }

    @Test
    @Transactional
    public void createFile() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File
        FileDTO fileDTO = fileMapper.toDto(file);
        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate + 1);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testFile.getDocumentOneDetails()).isEqualTo(DEFAULT_DOCUMENT_ONE_DETAILS);
        assertThat(testFile.getDocumentOne()).isEqualTo(DEFAULT_DOCUMENT_ONE);
        assertThat(testFile.getDocumentOneContentType()).isEqualTo(DEFAULT_DOCUMENT_ONE_CONTENT_TYPE);
        assertThat(testFile.getDocumentTwoDetails()).isEqualTo(DEFAULT_DOCUMENT_TWO_DETAILS);
        assertThat(testFile.getDocumentTwo()).isEqualTo(DEFAULT_DOCUMENT_TWO);
        assertThat(testFile.getDocumentTwoContentType()).isEqualTo(DEFAULT_DOCUMENT_TWO_CONTENT_TYPE);
        assertThat(testFile.getDocumentThreeDetails()).isEqualTo(DEFAULT_DOCUMENT_THREE_DETAILS);
        assertThat(testFile.getDocumentThree()).isEqualTo(DEFAULT_DOCUMENT_THREE);
        assertThat(testFile.getDocumentThreeContentType()).isEqualTo(DEFAULT_DOCUMENT_THREE_CONTENT_TYPE);

        // Validate the File in Elasticsearch
        File fileEs = fileSearchRepository.findOne(testFile.getId());
        assertThat(fileEs).isEqualToIgnoringGivenFields(testFile);
    }

    @Test
    @Transactional
    public void createFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File with an existing ID
        file.setId(1L);
        FileDTO fileDTO = fileMapper.toDto(file);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFiles() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList
        restFileMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].documentOneDetails").value(hasItem(DEFAULT_DOCUMENT_ONE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentOneContentType").value(hasItem(DEFAULT_DOCUMENT_ONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentOne").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ONE))))
            .andExpect(jsonPath("$.[*].documentTwoDetails").value(hasItem(DEFAULT_DOCUMENT_TWO_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentTwoContentType").value(hasItem(DEFAULT_DOCUMENT_TWO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentTwo").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_TWO))))
            .andExpect(jsonPath("$.[*].documentThreeDetails").value(hasItem(DEFAULT_DOCUMENT_THREE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentThreeContentType").value(hasItem(DEFAULT_DOCUMENT_THREE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentThree").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_THREE))));
    }

    @Test
    @Transactional
    public void getFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(file.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.documentOneDetails").value(DEFAULT_DOCUMENT_ONE_DETAILS.toString()))
            .andExpect(jsonPath("$.documentOneContentType").value(DEFAULT_DOCUMENT_ONE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentOne").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ONE)))
            .andExpect(jsonPath("$.documentTwoDetails").value(DEFAULT_DOCUMENT_TWO_DETAILS.toString()))
            .andExpect(jsonPath("$.documentTwoContentType").value(DEFAULT_DOCUMENT_TWO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentTwo").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_TWO)))
            .andExpect(jsonPath("$.documentThreeDetails").value(DEFAULT_DOCUMENT_THREE_DETAILS.toString()))
            .andExpect(jsonPath("$.documentThreeContentType").value(DEFAULT_DOCUMENT_THREE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentThree").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_THREE)));
    }

    @Test
    @Transactional
    public void getNonExistingFile() throws Exception {
        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        fileSearchRepository.save(file);
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file
        File updatedFile = fileRepository.findOne(file.getId());
        // Disconnect from session so that the updates on updatedFile are not directly saved in db
        em.detach(updatedFile);
        updatedFile
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentOneDetails(UPDATED_DOCUMENT_ONE_DETAILS)
            .documentOne(UPDATED_DOCUMENT_ONE)
            .documentOneContentType(UPDATED_DOCUMENT_ONE_CONTENT_TYPE)
            .documentTwoDetails(UPDATED_DOCUMENT_TWO_DETAILS)
            .documentTwo(UPDATED_DOCUMENT_TWO)
            .documentTwoContentType(UPDATED_DOCUMENT_TWO_CONTENT_TYPE)
            .documentThreeDetails(UPDATED_DOCUMENT_THREE_DETAILS)
            .documentThree(UPDATED_DOCUMENT_THREE)
            .documentThreeContentType(UPDATED_DOCUMENT_THREE_CONTENT_TYPE);
        FileDTO fileDTO = fileMapper.toDto(updatedFile);

        restFileMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testFile.getDocumentOneDetails()).isEqualTo(UPDATED_DOCUMENT_ONE_DETAILS);
        assertThat(testFile.getDocumentOne()).isEqualTo(UPDATED_DOCUMENT_ONE);
        assertThat(testFile.getDocumentOneContentType()).isEqualTo(UPDATED_DOCUMENT_ONE_CONTENT_TYPE);
        assertThat(testFile.getDocumentTwoDetails()).isEqualTo(UPDATED_DOCUMENT_TWO_DETAILS);
        assertThat(testFile.getDocumentTwo()).isEqualTo(UPDATED_DOCUMENT_TWO);
        assertThat(testFile.getDocumentTwoContentType()).isEqualTo(UPDATED_DOCUMENT_TWO_CONTENT_TYPE);
        assertThat(testFile.getDocumentThreeDetails()).isEqualTo(UPDATED_DOCUMENT_THREE_DETAILS);
        assertThat(testFile.getDocumentThree()).isEqualTo(UPDATED_DOCUMENT_THREE);
        assertThat(testFile.getDocumentThreeContentType()).isEqualTo(UPDATED_DOCUMENT_THREE_CONTENT_TYPE);

        // Validate the File in Elasticsearch
        File fileEs = fileSearchRepository.findOne(testFile.getId());
        assertThat(fileEs).isEqualToIgnoringGivenFields(testFile);
    }

    @Test
    @Transactional
    public void updateNonExistingFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Create the File
        FileDTO fileDTO = fileMapper.toDto(file);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        fileSearchRepository.save(file);
        int databaseSizeBeforeDelete = fileRepository.findAll().size();

        // Get the file
        restFileMockMvc.perform(delete("/api/files/{id}", file.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fileExistsInEs = fileSearchRepository.exists(file.getId());
        assertThat(fileExistsInEs).isFalse();

        // Validate the database is empty
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        fileSearchRepository.save(file);

        // Search the file
        restFileMockMvc.perform(get("/api/_search/files?query=id:" + file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].documentOneDetails").value(hasItem(DEFAULT_DOCUMENT_ONE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentOneContentType").value(hasItem(DEFAULT_DOCUMENT_ONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentOne").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_ONE))))
            .andExpect(jsonPath("$.[*].documentTwoDetails").value(hasItem(DEFAULT_DOCUMENT_TWO_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentTwoContentType").value(hasItem(DEFAULT_DOCUMENT_TWO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentTwo").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_TWO))))
            .andExpect(jsonPath("$.[*].documentThreeDetails").value(hasItem(DEFAULT_DOCUMENT_THREE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].documentThreeContentType").value(hasItem(DEFAULT_DOCUMENT_THREE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentThree").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_THREE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(File.class);
        File file1 = new File();
        file1.setId(1L);
        File file2 = new File();
        file2.setId(file1.getId());
        assertThat(file1).isEqualTo(file2);
        file2.setId(2L);
        assertThat(file1).isNotEqualTo(file2);
        file1.setId(null);
        assertThat(file1).isNotEqualTo(file2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileDTO.class);
        FileDTO fileDTO1 = new FileDTO();
        fileDTO1.setId(1L);
        FileDTO fileDTO2 = new FileDTO();
        assertThat(fileDTO1).isNotEqualTo(fileDTO2);
        fileDTO2.setId(fileDTO1.getId());
        assertThat(fileDTO1).isEqualTo(fileDTO2);
        fileDTO2.setId(2L);
        assertThat(fileDTO1).isNotEqualTo(fileDTO2);
        fileDTO1.setId(null);
        assertThat(fileDTO1).isNotEqualTo(fileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileMapper.fromId(null)).isNull();
    }
}
