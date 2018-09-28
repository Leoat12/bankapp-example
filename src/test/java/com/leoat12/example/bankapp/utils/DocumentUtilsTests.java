package com.leoat12.example.bankapp.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DocumentUtilsTests {

    @Test
    public void validDocument(){
        assertThat(DocumentUtils.isCPF("54510411057"), equalTo(true));
        assertThat(DocumentUtils.printCPF("54510411057"), equalTo("545.104.110-57"));
    }

    @Test
    public void testDocumentFormats(){
        assertThat(DocumentUtils.isCPF("54510411057"), equalTo(true));
        assertThat(DocumentUtils.isCPF("545.104.110-57"), equalTo(true));
        assertThat(DocumentUtils.isCPF("545104110-57"), equalTo(true));
    }

    @Test
    public void invalidDocumentWithRepetitiveNumbers(){
        assertThat(DocumentUtils.isCPF("11111111111"), equalTo(false));
    }

    @Test
    public void invalidDocumentWithLessCharacters(){
        assertThat(DocumentUtils.isCPF("44489762"), equalTo(false));
    }

    @Test
    public void invalidDocumentWithLetters(){
        assertThat(DocumentUtils.isCPF("1A86R568A78"), equalTo(false));
    }

    @Test
    public void invalidDocumentWithWrongValidationDigit(){
        assertThat(DocumentUtils.isCPF("54510411056"), equalTo(false));
    }
}
