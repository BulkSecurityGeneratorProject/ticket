package com.bluehoods.ticket.cucumber.stepdefs;

import com.bluehoods.ticket.TicketApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TicketApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
