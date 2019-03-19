package controllers;

import models.Cat;
import org.w3c.dom.Attr;
import weka.core.*;

import java.util.ArrayList;
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
    private static FastVector<Attribute> createAttributeFromValues(String[] values) {
        FastVector<Attribute> attrs = new FastVector<>(values.length - 1);
        FastVector<String> fvVals = new FastVector<>(2);
        fvVals.add(TRUE);
        fvVals.add(FALSE);

        // Add all from the second item (where the first item is the attribute name)
        for (int i = 1; i < values.length; ++i) {
            attrs.add(new Attribute(values[i], fvVals));
        }

        // Create the attribute with the attribute name and the fast vector
        return attrs;
    }

    /**
     * Creates a fast vector relation between attributes and values.
     * @return the generated attributes-values relation.
     */
    private static FastVector<Attribute> createWekaAttributes() {
        FastVector<Attribute> fvWekaAttributes = new FastVector<>();

        for (int i = 0; i < ATTRIBUTE_COUNT; ++i) {
            fvWekaAttributes.addAll(createAttributeFromValues(Cat.Values[i]));
        }

        return fvWekaAttributes;
    }

    /**
     * Generates an instance with random values.
     * @return the hash of the instance and the generated instance.
     */
    private static GeneratedInstance generateInstance() {
        Instance instance = new DenseInstance(fvWekaAttributes.size());

        int instanceHash = 0;
        int attrPos = 0;

        for (int mapPos = 0; mapPos < ATTRIBUTE_COUNT; ++mapPos) {
            // Retrieve the attribute values
            String[] values = Cat.Values[mapPos];

            // Choose a value
            int randomValuePos = ThreadLocalRandom.current().nextInt(1, values.length);

            // Set the value (and remove the first value, which is the attribute)
            instance.setValue(
                fvWekaAttributes.elementAt(attrPos + randomValuePos - 1), TRUE);

            // Update the instance hash
            instanceHash |= 1 << ((mapPos + 1) * randomValuePos);

            // Move cursor
            attrPos += values.length - 2;
        }

        for (int i = 0; i < fvWekaAttributes.size(); i++) {
            Attribute attr = fvWekaAttributes.elementAt(i);
            if (!instance.stringValue(attr).equals(TRUE)) {
                instance.setValue(attr, FALSE);
            }
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
