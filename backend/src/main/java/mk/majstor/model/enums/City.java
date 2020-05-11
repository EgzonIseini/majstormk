package mk.majstor.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum City {
    Berovo("Берово"),
    Bitola("Битола"),
    Bogdanci("Богданци"),
    Valandovo("Валандово"),
    Veles("Велес"),
    Vinica("Виница"),
    Gevgelija("Гевгелија"),
    Gostivar("Гостивар"),
    Debar("Дебар"),
    Delcevo("Делчево"),
    DemirKapija("Демир Капија"),
    DemirHisar("Демир Хисар"),
    Kavadarci("Кавадарци"),
    Kicevo("Кичево"),
    Kocani("Кочани"),
    Kratovo("Кратово"),
    KrivaPalanka("Крива Паланка"),
    Krusevo("Крушево"),
    Kumanovo("Куманово"),
    MakedonskaKamenica("Македонска Каменица"),
    MakedonskiBrod("Македонски Брод"),
    Negotino("Неготино"),
    Ohrid("Охрид"),
    Pehcevo("Пехчево"),
    Prilep("Прилеп"),
    Probistip("Пробиштип"),
    Radovis("Радовиш"),
    Resen("Ресен"),
    SvetiNikole("Свети Николе"),
    Skopje("Скопје"),
    Struga("Струга"),
    Strumica("Струмица"),
    Tetovo("Тетово"),
    Stip("Штип");

    private String value;

    City(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, City> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static
    {
        for(City city : City.values())
        {
            lookup.put(city.getValue(), city);
        }
    }

    //This method can be used for reverse lookup purpose
    public static City get(String cityName)
    {
        return lookup.get(cityName);
    }
}
