package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(0);
    }

    @Test
    public void getNombreAnneeAncienneteNminus2() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(2);
    }

    @Test
    public void getNombreAnneeAncienneteNull() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(0);
    }

    @Test
    public void getNombreAnneeAncienneteNplus2() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete.intValue()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(prime);

    }

    @Test
    public void augmenterSalairePlus25() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2500d);
        double pourcentage = 25.00;

        //When
        e.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(e.getSalaire()).isEqualTo(3125);
    }

    @Test
    public void augmenterSalaireMinus25() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2500d);
        double pourcentage = -25.00;

        Assertions.assertThatThrownBy(() -> {
                    //When
                    e.augmenterSalaire(pourcentage);
                }
                //Then
        ).isInstanceOf(Exception.class).hasMessage("Le pourcentage ne peut pas etre negatif, veuillez entrer une valeur valide !");
    }

    @Test
    public void augmenterSalaireEqual0() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(2500d);
        double pourcentage = 0.00;

        //When
        e.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(e.getSalaire()).isEqualTo(2500d);
    }

    @Test
    public void augmenterSalaireNull() throws EmployeException {
        //Given
        Employe e = new Employe();
        e.setSalaire(null);
        double pourcentage = 10.00;

        Assertions.assertThatThrownBy(() -> {
                    //When
                    e.augmenterSalaire(pourcentage);
                }
                //Then
        ).isInstanceOf(Exception.class).hasMessage("Le salaire est null !");
    }
}
