package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private Owner owner = new Owner(5L, "Bob", "Smith");
    private BindingResult result = mock(BindingResult.class);

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    @Test
    void processCreationForm() {
        //given
        when(result.hasErrors()).thenReturn(false);
        when(ownerService.save(any())).thenReturn(owner);

        //when
        String actual = controller.processCreationForm(owner, result);

        //then
        assertEquals("redirect:/owners/5", actual);
        then(ownerService).should().save(owner);
    }

    @Test
    void processCreationFormHasErrors() {
        //given
        when(result.hasErrors()).thenReturn(true);

        //when
        String actual = controller.processCreationForm(owner, result);

        //then
        assertEquals("owners/createOrUpdateOwnerForm", actual);
        then(ownerService).should(never()).save(any());
    }

}