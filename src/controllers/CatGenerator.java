package controllers;

import javafx.util.Pair;
import models.Cat;
import weka.core.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CatGenerator {
    private static final int ATTRIBUTES_COUNT = Cat.Values.length;
    public static final FastVector<Attribute> fvWekaAttributes = createWekaAttributes();

    private static Attribute createAttributeFromValues(String[] values) {
        FastVector<String> fvVals = new FastVector<>(values.length - 1);

        // Add all from the second item (where the first item is the attribute name)
        fvVals.addAll(Arrays.asList(values).subList(1, values.length));

        // Create the attribute with the attribute name and the fast vector
        return new Attribute(values[0], fvVals);
    }

    private static FastVector<Attribute> createWekaAttributes() {
        FastVector<Attribute> fvWekaAttributes = new FastVector<>(ATTRIBUTES_COUNT);

        for (int i = 0; i < ATTRIBUTES_COUNT; ++i) {
            fvWekaAttributes.add(
                createAttributeFromValues(Cat.Values[i]));
        }

        return fvWekaAttributes;
    }

    private static Pair<Integer, Instance> generateInstance() {
        Instance instance = new DenseInstance(ATTRIBUTES_COUNT);

        int instanceHash = 0;
        for (int attrPos = 0; attrPos < ATTRIBUTES_COUNT; ++attrPos) {
            // Retrieve the attribute values
            String[] values = Cat.Values[attrPos];

            // Choose a value
            int randomValuePos = ThreadLocalRandom.current().nextInt(1, values.length);

            // Set the value
            instance.setValue(
                fvWekaAttributes.elementAt(attrPos), Cat.Values[attrPos][randomValuePos]);

            // Update the instance hash
            instanceHash |= 1 << ((attrPos + 1) * randomValuePos);
        }

        return new Pair<>(instanceHash, instance);
    }

    private static boolean isHashUnique(int[] hashes, int newHash) {
        for (int hash : hashes) {
            if (hash == newHash) {
                return false;
            }
        }
        return true;
    }

    public static Instances generatesInstances(int count) {
        Instances instances = new Instances("Rel", fvWekaAttributes, count);
        int[] instances_hashes = new int[count];

        Pair<Integer, Instance> instance;
        for (int i = 0; i < count; ++i) {
            do {
                instance = generateInstance();
            } while (!isHashUnique(instances_hashes, instance.getKey()));
            instances.add(instance.getValue());
            instances_hashes[i] = instance.getKey();
        }

        return instances;
    }
}
