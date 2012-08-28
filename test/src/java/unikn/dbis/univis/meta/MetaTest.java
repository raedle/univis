package unikn.dbis.univis.meta;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import unikn.dbis.univis.hibernate.util.HibernateUtil;

/**
 * TODO: document me!!!
 * <p/>
 * <code>MetaTest</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 07.04.2006
 * Time: 17:33:04
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: MetaTest.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class MetaTest extends TestCase {

    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private Long cubeId;
    private Long dimensionId;

    /*
    public void testInsertCube() {
        VCubeImpl cube = new VCubeImpl();
        cube.setTableName("STUDENTS");

        Session session = sessionFactory.openSession();
        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(cube);
        trx.commit();
        session.close();

        session = sessionFactory.openSession();
        VCubeImpl loadedCube = (VCubeImpl) session.load(VCubeImpl.class, cube.getId());

        assertEquals(cube.getId(), loadedCube.getId());
        assertEquals(cube.getDimensions(), loadedCube.getDimensions());

        cubeId = cube.getId();
    }

    public void testAddDimensionToCube() {
        testInsertCube();

        Session session = sessionFactory.openSession();
        VCubeImpl cube = (VCubeImpl) session.load(VCubeImpl.class, cubeId);

        VDimensionImpl dimension = new VDimensionImpl();
        dimension.setDragable(true);
        dimension.setTableName("BLUEP_ZEIT");

        cube.addDimension(dimension);

        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(cube);
        trx.commit();
        session.close();

        session = sessionFactory.openSession();
        VCubeImpl loadedCube = (VCubeImpl) session.load(VCubeImpl.class, cube.getId());

        assertEquals(cube.getDimensions(), loadedCube.getDimensions());

        session.close();

        dimensionId = dimension.getId();
    }

    public void testAddSubDimensionToDimension() {
        testAddDimensionToCube();

        Session session = sessionFactory.openSession();
        VDimensionImpl dimension = (VDimensionImpl) session.load(VDimensionImpl.class, dimensionId);

        VDimensionImpl subDimension = new VDimensionImpl();
        subDimension.setDragable(false);
        subDimension.setTableName("DIM_HALBJAHRE");

        dimension.addSubDimension(subDimension);

        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(dimension);
        trx.commit();
        session.close();

        session = sessionFactory.openSession();
        VDimensionImpl loadedDimension = (VDimensionImpl) session.load(VDimensionImpl.class, dimension.getId());

        for (int i = 0; i < dimension.getSubDimensions().size(); i++)
        assertEquals(dimension.getSubDimensions().get(i), loadedDimension.getSubDimensions().get(i));
    }
    */
}