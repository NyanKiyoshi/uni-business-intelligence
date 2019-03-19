package controllers;

import models.Cat;
import weka.core.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates weka datasets for Cat instances.
 */
public class CatGenerator {

    public static final String TRUE = "True";
    public static final String FALSE = "False";

    private static final class GeneratedInstance {
        int hash;
        Instance instance;

        GeneratedInstance(int hash, Instance instance) {
            this.hash = hash;
            this.instance = instance;
        }
    }

    public static final int ATTRIBUTE_COUNT = Cat.Values.length;
    public static final FastVector<Attribute> fvWekaAttributes = createWekaAttributes();

    /**
     * Creates a weka attribute from given values.
     * @param values the values of the attribute.
     * @return the generated weka attribute.
     */
    private static Attribute createAttributeFromValues(String[] values) {
        FastVector<String> fvVals = new FastVector<>(values.length - 1);

        // Add all from the second item (where the first item is the attribute name)
        fvVals.addAll(Arrays.asList(values).subList(1, values.length));

        // Create the attribute with the attribute name and the fast vector
        return new Attribute(values[0], fvVals);
    }

    /**
     * Creates a fast vector relation between attributes and values.
     * @return the generated attributes-values relation.
     */
    private static FastVector<Attribute> createWekaAttributes() {
        FastVector<Attribute> fvWekaAttributes = new FastVector<>(ATTRIBUTE_COUNT);

        for (int i = 0; i < ATTRIBUTE_COUNT; ++i) {
            fvWekaAttributes.add(
                createAttributeFromValues(Cat.Values[i]));
        }

        FastVector<String> fvVals = new FastVector<>(2);
        fvVals.add(TRUE);
        fvVals.add(FALSE);
        fvWekaAttributes.add(new Attribute("Is Selected", fvVals));

        return fvWekaAttributes;
    }

    /**
     * Generates an instance with random values.
     * @return the hash of the instance and the generated instance.
     */
    private static GeneratedInstance generateInstance() {
        Instance instance = new DenseInstance(fvWekaAttributes.size());

        int instanceHash = 0;
        for (int attrPos = 0; attrPos < ATTRIBUTE_COUNT; ++attrPos) {
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

        return new GeneratedInstance(instanceHash, instance);
    }

    /**
     * Checks whether a given hash is unique or not.
     * @param hashes the generated hashes (unique)
     * @param newHash the new hash to compare from
     * @return whether the new hash is unique or not (not in hashes)
     */
    private static boolean isHashUnique(int[] hashes, int newHash) {
        for (int hash : hashes) {
            if (hash == newHash) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a given count of random cat instances.
     * @param count the count to generate
     * @return the generated instances
     */
    public static Instances generatesInstances(int count) {
        Instances instances = new Instances("Rel", fvWekaAttributes, count);
        int[] instances_hashes = new int[count];

        GeneratedInstance instance;
        for (int i = 0; i < count; ++i) {
            do {
                instance = generateInstance();
            } while (!isHashUnique(instances_hashes, instance.hash));
            instances.add(instance.instance);
            instances_hashes[i] = instance.hash;
        }

        return instances;
    }
}
