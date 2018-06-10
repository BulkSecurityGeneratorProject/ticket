package com.bluehoods.ticket.web.rest;

import com.bluehoods.ticket.TicketApp;

import com.bluehoods.ticket.domain.Tasks;
import com.bluehoods.ticket.domain.Project;
import com.bluehoods.ticket.repository.TasksRepository;
import com.bluehoods.ticket.service.TasksService;
import com.bluehoods.ticket.repository.search.TasksSearchRepository;
import com.bluehoods.ticket.service.dto.TasksDTO;
import com.bluehoods.ticket.service.mapper.TasksMapper;
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

import com.bluehoods.ticket.domain.enumeration.TaskStatus;
/**
 * Test class for the TasksResource REST controller.
 *
 * @see TasksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketApp.class)
public class TasksResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final TaskStatus DEFAULT_TASK_STATUS = TaskStatus.STATUS_ACTIVE;
    private static final TaskStatus UPDATED_TASK_STATUS = TaskStatus.STATUS_DONE;

    private static final String DEFAULT_START = "AAAAAAAAAA";
    private static final String UPDATED_START = "BBBBBBBBBB";

    private static final String DEFAULT_END = "AAAAAAAAAA";
    private static final String UPDATED_END = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Boolean DEFAULT_START_IS_MILESTONE = false;
    private static final Boolean UPDATED_START_IS_MILESTONE = true;

    private static final Boolean DEFAULT_END_IS_MILESTONE = false;
    private static final Boolean UPDATED_END_IS_MILESTONE = true;

    private static final String DEFAULT_DEPENDS = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROGRESS = 1;
    private static final Integer UPDATED_PROGRESS = 2;

    private static final Integer DEFAULT_SELECTED_ROW = 1;
    private static final Integer UPDATED_SELECTED_ROW = 2;

    private static final Boolean DEFAULT_CAN_WRITE = false;
    private static final Boolean UPDATED_CAN_WRITE = true;

    private static final Boolean DEFAULT_CAN_WRITE_ON_PARENT = false;
    private static final Boolean UPDATED_CAN_WRITE_ON_PARENT = true;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TasksSearchRepository tasksSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTasksMockMvc;

    private Tasks tasks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TasksResource tasksResource = new TasksResource(tasksService);
        this.restTasksMockMvc = MockMvcBuilders.standaloneSetup(tasksResource)
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
    public static Tasks createEntity(EntityManager em) {
        Tasks tasks = new Tasks()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .level(DEFAULT_LEVEL)
            .taskStatus(DEFAULT_TASK_STATUS)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .duration(DEFAULT_DURATION)
            .startIsMilestone(DEFAULT_START_IS_MILESTONE)
            .endIsMilestone(DEFAULT_END_IS_MILESTONE)
            .depends(DEFAULT_DEPENDS)
            .description(DEFAULT_DESCRIPTION)
            .progress(DEFAULT_PROGRESS)
            .selectedRow(DEFAULT_SELECTED_ROW)
            .canWrite(DEFAULT_CAN_WRITE)
            .canWriteOnParent(DEFAULT_CAN_WRITE_ON_PARENT);
        // Add required entity
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        tasks.setProject(project);
        return tasks;
    }

    @Before
    public void initTest() {
        tasksSearchRepository.deleteAll();
        tasks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasks() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate + 1);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTasks.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTasks.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testTasks.getTaskStatus()).isEqualTo(DEFAULT_TASK_STATUS);
        assertThat(testTasks.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTasks.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testTasks.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTasks.isStartIsMilestone()).isEqualTo(DEFAULT_START_IS_MILESTONE);
        assertThat(testTasks.isEndIsMilestone()).isEqualTo(DEFAULT_END_IS_MILESTONE);
        assertThat(testTasks.getDepends()).isEqualTo(DEFAULT_DEPENDS);
        assertThat(testTasks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTasks.getProgress()).isEqualTo(DEFAULT_PROGRESS);
        assertThat(testTasks.getSelectedRow()).isEqualTo(DEFAULT_SELECTED_ROW);
        assertThat(testTasks.isCanWrite()).isEqualTo(DEFAULT_CAN_WRITE);
        assertThat(testTasks.isCanWriteOnParent()).isEqualTo(DEFAULT_CAN_WRITE_ON_PARENT);

        // Validate the Tasks in Elasticsearch
        Tasks tasksEs = tasksSearchRepository.findOne(testTasks.getId());
        assertThat(tasksEs).isEqualToIgnoringGivenFields(testTasks);
    }

    @Test
    @Transactional
    public void createTasksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks with an existing ID
        tasks.setId(1L);
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get all the tasksList
        restTasksMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].taskStatus").value(hasItem(DEFAULT_TASK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].startIsMilestone").value(hasItem(DEFAULT_START_IS_MILESTONE.booleanValue())))
            .andExpect(jsonPath("$.[*].endIsMilestone").value(hasItem(DEFAULT_END_IS_MILESTONE.booleanValue())))
            .andExpect(jsonPath("$.[*].depends").value(hasItem(DEFAULT_DEPENDS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS)))
            .andExpect(jsonPath("$.[*].selectedRow").value(hasItem(DEFAULT_SELECTED_ROW)))
            .andExpect(jsonPath("$.[*].canWrite").value(hasItem(DEFAULT_CAN_WRITE.booleanValue())))
            .andExpect(jsonPath("$.[*].canWriteOnParent").value(hasItem(DEFAULT_CAN_WRITE_ON_PARENT.booleanValue())));
    }

    @Test
    @Transactional
    public void getTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tasks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.taskStatus").value(DEFAULT_TASK_STATUS.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.startIsMilestone").value(DEFAULT_START_IS_MILESTONE.booleanValue()))
            .andExpect(jsonPath("$.endIsMilestone").value(DEFAULT_END_IS_MILESTONE.booleanValue()))
            .andExpect(jsonPath("$.depends").value(DEFAULT_DEPENDS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.progress").value(DEFAULT_PROGRESS))
            .andExpect(jsonPath("$.selectedRow").value(DEFAULT_SELECTED_ROW))
            .andExpect(jsonPath("$.canWrite").value(DEFAULT_CAN_WRITE.booleanValue()))
            .andExpect(jsonPath("$.canWriteOnParent").value(DEFAULT_CAN_WRITE_ON_PARENT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTasks() throws Exception {
        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);
        tasksSearchRepository.save(tasks);
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Update the tasks
        Tasks updatedTasks = tasksRepository.findOne(tasks.getId());
        // Disconnect from session so that the updates on updatedTasks are not directly saved in db
        em.detach(updatedTasks);
        updatedTasks
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .level(UPDATED_LEVEL)
            .taskStatus(UPDATED_TASK_STATUS)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .duration(UPDATED_DURATION)
            .startIsMilestone(UPDATED_START_IS_MILESTONE)
            .endIsMilestone(UPDATED_END_IS_MILESTONE)
            .depends(UPDATED_DEPENDS)
            .description(UPDATED_DESCRIPTION)
            .progress(UPDATED_PROGRESS)
            .selectedRow(UPDATED_SELECTED_ROW)
            .canWrite(UPDATED_CAN_WRITE)
            .canWriteOnParent(UPDATED_CAN_WRITE_ON_PARENT);
        TasksDTO tasksDTO = tasksMapper.toDto(updatedTasks);

        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isOk());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTasks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTasks.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testTasks.getTaskStatus()).isEqualTo(UPDATED_TASK_STATUS);
        assertThat(testTasks.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTasks.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testTasks.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTasks.isStartIsMilestone()).isEqualTo(UPDATED_START_IS_MILESTONE);
        assertThat(testTasks.isEndIsMilestone()).isEqualTo(UPDATED_END_IS_MILESTONE);
        assertThat(testTasks.getDepends()).isEqualTo(UPDATED_DEPENDS);
        assertThat(testTasks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTasks.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testTasks.getSelectedRow()).isEqualTo(UPDATED_SELECTED_ROW);
        assertThat(testTasks.isCanWrite()).isEqualTo(UPDATED_CAN_WRITE);
        assertThat(testTasks.isCanWriteOnParent()).isEqualTo(UPDATED_CAN_WRITE_ON_PARENT);

        // Validate the Tasks in Elasticsearch
        Tasks tasksEs = tasksSearchRepository.findOne(testTasks.getId());
        assertThat(tasksEs).isEqualToIgnoringGivenFields(testTasks);
    }

    @Test
    @Transactional
    public void updateNonExistingTasks() throws Exception {
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Create the Tasks
        TasksDTO tasksDTO = tasksMapper.toDto(tasks);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasksDTO)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);
        tasksSearchRepository.save(tasks);
        int databaseSizeBeforeDelete = tasksRepository.findAll().size();

        // Get the tasks
        restTasksMockMvc.perform(delete("/api/tasks/{id}", tasks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tasksExistsInEs = tasksSearchRepository.exists(tasks.getId());
        assertThat(tasksExistsInEs).isFalse();

        // Validate the database is empty
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);
        tasksSearchRepository.save(tasks);

        // Search the tasks
        restTasksMockMvc.perform(get("/api/_search/tasks?query=id:" + tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].taskStatus").value(hasItem(DEFAULT_TASK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].startIsMilestone").value(hasItem(DEFAULT_START_IS_MILESTONE.booleanValue())))
            .andExpect(jsonPath("$.[*].endIsMilestone").value(hasItem(DEFAULT_END_IS_MILESTONE.booleanValue())))
            .andExpect(jsonPath("$.[*].depends").value(hasItem(DEFAULT_DEPENDS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS)))
            .andExpect(jsonPath("$.[*].selectedRow").value(hasItem(DEFAULT_SELECTED_ROW)))
            .andExpect(jsonPath("$.[*].canWrite").value(hasItem(DEFAULT_CAN_WRITE.booleanValue())))
            .andExpect(jsonPath("$.[*].canWriteOnParent").value(hasItem(DEFAULT_CAN_WRITE_ON_PARENT.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tasks.class);
        Tasks tasks1 = new Tasks();
        tasks1.setId(1L);
        Tasks tasks2 = new Tasks();
        tasks2.setId(tasks1.getId());
        assertThat(tasks1).isEqualTo(tasks2);
        tasks2.setId(2L);
        assertThat(tasks1).isNotEqualTo(tasks2);
        tasks1.setId(null);
        assertThat(tasks1).isNotEqualTo(tasks2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasksDTO.class);
        TasksDTO tasksDTO1 = new TasksDTO();
        tasksDTO1.setId(1L);
        TasksDTO tasksDTO2 = new TasksDTO();
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO2.setId(tasksDTO1.getId());
        assertThat(tasksDTO1).isEqualTo(tasksDTO2);
        tasksDTO2.setId(2L);
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO1.setId(null);
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tasksMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tasksMapper.fromId(null)).isNull();
    }
}
