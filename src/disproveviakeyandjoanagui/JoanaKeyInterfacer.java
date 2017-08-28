/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disproveviakeyandjoanagui;

import edu.kit.joana.ifc.sdg.graph.SDG;
import edu.kit.joana.ifc.sdg.graph.SDGEdge;
import edu.kit.joana.ifc.sdg.graph.SDGNodeTuple;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import joanakeyrefactoring.StateSaver;
import joanakeyrefactoring.SummaryEdgeAndMethodToCorresData;
import joanakeyrefactoring.ViolationsWrapper;
import joanakeyrefactoring.javaforkeycreator.JavaForKeyCreator;
import joanakeyrefactoring.javaforkeycreator.PointsToGenerator;
import joanakeyrefactoring.staticCG.JCallGraph;
import joanakeyrefactoring.staticCG.javamodel.StaticCGJavaMethod;

/**
 *
 * @author holger
 */
public class JoanaKeyInterfacer {

    private ViolationsWrapper violationsWrapper;
    private JavaForKeyCreator javaForKeyCreator;
    private SummaryEdgeAndMethodToCorresData summaryEdgeToCorresData;

    public JoanaKeyInterfacer(
            ViolationsWrapper violationsWrapper,
            String pathToJavaSource,
            JCallGraph callGraph,
            SDG sdg,
            StateSaver stateSaver) throws IOException {
        this.violationsWrapper = violationsWrapper;
        this.javaForKeyCreator = new JavaForKeyCreator(pathToJavaSource, callGraph, sdg, stateSaver);
        Map<SDGEdge, StaticCGJavaMethod> summaryEdgesAndCorresJavaMethods = violationsWrapper.getSummaryEdgesAndCorresJavaMethods();
        summaryEdgeToCorresData = new SummaryEdgeAndMethodToCorresData(
                summaryEdgesAndCorresJavaMethods,
                sdg,
                javaForKeyCreator);
    }

    public String getKeyContractFor(SDGNodeTuple formalTuple, StaticCGJavaMethod methodCorresToSE) {
        return summaryEdgeToCorresData.getContractFor(formalTuple);
    }

    public String getLoopInvariantFor(SDGEdge e, int index) {
        return summaryEdgeToCorresData.getLoopInvariantFor(e, index);
    }

    public void setLoopInvariantFor(SDGEdge e, int index, String val) {
        summaryEdgeToCorresData.setLoopInvariantFor(e, index, val);
    }

    public void resetLoopInvariant(SDGEdge currentSelectedEdge, int newValue) {
        summaryEdgeToCorresData.resetLoopInvariant(currentSelectedEdge, newValue);
    }
}
