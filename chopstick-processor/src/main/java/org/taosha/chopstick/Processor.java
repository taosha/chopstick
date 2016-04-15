package org.taosha.chopstick;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import org.taosha.nit.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import chopstick.Name;

@Service(javax.annotation.processing.Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("chopstick.Name")
public class Processor extends javax.annotation.processing.AbstractProcessor {

    private Map<TypeElement, Set<VariableElement>> injectorMap;
    private TypeMirror nameType;
    private Filer filer;
    private Mustache template;

    @Override public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        nameType = processingEnv.getElementUtils().getTypeElement(Name.class.getSimpleName()).asType();
        injectorMap = new HashMap<TypeElement, Set<VariableElement>>();
        template = new DefaultMustacheFactory().compile("templates/injector.mustache");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(Name.class)) {
            validate(e);

            if (ElementKind.FIELD.equals(e.getKind())) {
                collect((TypeElement) e.getEnclosingElement(), (VariableElement) e);
            }
        }

        for (Map.Entry<TypeElement, Set<VariableElement>> entry : injectorMap.entrySet()) {
            generateInjector(entry.getKey(), entry.getValue());
        }

        return true;
    }

    private void validate(Element e) {
        // TODO: Validate
    }

    private void collect(TypeElement type, VariableElement field) {
        Set<VariableElement> set = injectorMap.get(type.toString());
        if (set == null) {
            set = new HashSet<VariableElement>();
            injectorMap.put(type, set);
        }
        set.add(field);
    }

    private void generateInjector(TypeElement targetType, Set<VariableElement> fields) {
        Map<String, Object> context = new HashMap<String, Object>();

        // Get package name and class name
        String className = targetType.getSimpleName() + "ChopstickInjector";
        Element enclosingElement = targetType.getEnclosingElement();
        while (!ElementKind.PACKAGE.equals(enclosingElement.getKind())) {
            className = enclosingElement.getSimpleName() + className;
            enclosingElement = enclosingElement.getEnclosingElement();
        }
        String packageName = enclosingElement.getSimpleName().toString();
        context.put("packageName", packageName);
        context.put("className", className);

        // Get facade methods
        List<FacadeMethod> facadeMethods = new ArrayList<FacadeMethod>(fields.size());
        for (VariableElement e : fields) {
            FacadeMethod m = new FacadeMethod();

            out:
            for (AnnotationMirror annotationMirror : e.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().equals(nameType)) {
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
                        if (entry.getKey().getSimpleName().toString().equals("value")) {
                            m.annotation = (String) entry.getValue().getValue();
                            break out;
                        }
                    }
                }
            }

            m.name = e.getSimpleName().toString();
            m.type = e.asType().toString();

            facadeMethods.add(m);
        }
        context.put("facadeMethods", facadeMethods);

        Writer writer = null;
        try {
            String qualifierName = packageName.length() > 0 ? packageName + "." + className : className;
            writer = filer.createSourceFile(qualifierName).openWriter();
            template.execute(writer, context);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate injector class for: " + targetType, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // Silent
                }
            }
        }
    }

    static class FacadeMethod {
        String annotation;
        String type;
        String name;
    }
}
