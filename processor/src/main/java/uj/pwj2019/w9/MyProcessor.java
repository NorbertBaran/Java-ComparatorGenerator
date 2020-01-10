package uj.pwj2019.w9;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes({"uj.pwj2019.w9.MyComparable", "uj.pwj2019.w9.ComparePriority"})
public class MyProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + annotation);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + annotatedElements);
            if(annotation.toString().equals("uj.pwj2019.w9.MyComparable"))
                annotatedElements.forEach(this::processElement);
        }
        return true;
    }


    private void processElement(Element e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Producing " + e);
        TypeElement clazz = (TypeElement) e;
        String className = clazz.getQualifiedName().toString();


        try {




            /*Class<?> unitClass = Class.forName(className);
            //Class<?> unitClass = clazz.getClass();


            for(Field field : unitClass.getDeclaredFields()){
                Class type = field.getType();
                String name = field.getName();
                Annotation[] annotation = field.getDeclaredAnnotations();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Type: " + type.getName() + " Name: " + name + "Annotation: "+ annotation.toString());
            }*/


        } catch (Exception ex) {
            ex.printStackTrace();
        }



        try {

            JavaFileObject file = processingEnv.getFiler().createSourceFile(className + "Comparator");
            String packageName = packageName(className);
            try (PrintWriter out = new PrintWriter(file.openWriter())) {

                String comparator="package uj.pwj2019.w9;\n" +
                        "\n" +
                        "public class SecretDataComparator {\n" +
                        "\n" +
                        "    public int compare("+className+" data1, "+className+" data2) {\n" +
                        "        return 0;\n" +
                        "    }\n" +
                        "}\n";

                out.write(comparator);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String packageName(String className) {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        return packageName;
    }



}