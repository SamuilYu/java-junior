package com.acme.edu;

import com.acme.edu.command.LoggerCommand;
import com.acme.edu.saver.ConsoleSaver;
import com.acme.edu.saver.LoggerSaver;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class LoggerControllerTest {

    private LoggerSaver stubSaver;
    private LoggerController controller;
    private LoggerCommand command;

    @Before
    public void setUp() {
        stubSaver = mock(ConsoleSaver.class);
        controller = new LoggerController(stubSaver);
        command = mock(LoggerCommand.class);
    }

    @Test
    public void shouldNotLogWhenFirstMessage() {
        //region given
        when(command.getDecoratedSelf()).thenReturn("str");
        //endregion

        //region when
        controller.log(command);
        //endregion

        //region then
        verify(stubSaver, times(0)).save(any());
        //endregion
    }

    @Test
    public void shouldLogDecoratedWhenAllowedFlush() {
        //region given
        when(command.getDecoratedSelf()).thenReturn("str");
        when(command.checkFlush(any(LoggerCommand.class))).thenReturn(true);
        //endregion

        //region when
        controller.log(command);
        controller.log(command);
        //endregion

        //region then
        verify(command, times(1)).checkFlush(command);
        verify(command, times(1)).getDecoratedSelf();
        verify(stubSaver, times(1)).save(anyString());
        //endregion
    }

    @Test
    public void shouldAccumulateButNotLogWhenDisallowedFlush() {
        //region given
        when(command.getDecoratedSelf()).thenReturn("str");
        when(command.checkFlush(any(LoggerCommand.class))).thenReturn(false);
        //endregion

        //region when
        controller.log(command);
        controller.log(command);
        //endregion

        //region then
        verify(command, times(1)).checkFlush(command);
        verify(command, times(1)).accumulate(command);
        verify(stubSaver, times(0)).save(anyString());
        //endregion
    }
}
