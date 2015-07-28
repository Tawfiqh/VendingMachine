import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class Gasket extends JApplet {
	/**
	 *  Starting code for the Sierpinski Gasket practical
	 *  Modified October 2012 to make automatic rescaling behave properly!
	 */
	private static final long serialVersionUID = 1L;
	// this assignment just keeps the compiler happy
	final int defaultSteps = 3, minSteps = 0, maxSteps = 12;
	static final int viewWidth = 500, viewHeight = 500;

	static JPanel control;
	static JPanel view;
	static Box box;
	static SpinnerModel stepsSpinner;
	static int numSteps;
	static Gasket applet;
	static Point a, b, c, base, centre;
	static boolean dragging, interior;
	static double abr, bcr, car;

	public static void main(String[] args) {
		JFrame outerFrame = new JFrame(); 
		outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outerFrame.setTitle( "Sierpinski Gasket");
		outerFrame.setSize( new Dimension( Gasket.viewWidth, (int) 1.2* Gasket.viewHeight));
		applet = new Gasket(); // The applet
		applet.init(); // initialise the applet
		outerFrame.getContentPane().add( applet); // put into frame
		outerFrame.setVisible( true);
		rescale();
		clear();
	}  // end of `main'

	public void init() {
		final int[] setSteps = { 2, 4, 8};
		final int numberButtonSize = 20;
		dragging = interior = false;
		abr = 0.5;
		bcr = 0.25;
		car  = 0.33;
		setEquilateralTriangle();
		
		/* 
		 * The outermost Frame (outerFrame) contains a Box which
		 * is used to vertically align two JPanels (control and view);
		 * control contains the buttons and labels, and view is where
		 * the Gasket is displayed
		 */
		control = new JPanel(); view = new JPanel();
		// add components to the control JPanel
		
		// Add a `draw' button
		JButton draw = new JButton( "Draw");
		draw.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {	
				clear();
				gasket( getSteps());
				redraw();
			}});
		control.add( draw);

		// Add a quit button
		JButton quit = new JButton("Quit");
		quit.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {	
				System.exit( 0);
			}});
		control.add( quit);
		
		// Add a text label, and a sequence of small buttons -
		// one for each number in the array setSteps.  Each button
		// sets the text in the following label accordingly
		control.add( new JLabel( "Set Steps:"));
		for ( int s=0 ; s<setSteps.length ; s++ ) {
			JButton b = new JButton( setSteps[s]+"");
			b.setPreferredSize( new Dimension(
					numberButtonSize, numberButtonSize));
			b.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e) {
					applet.setSteps( Integer.parseInt(
							((JButton) e.getSource()).getText()));
				}});
			control.add( b);
		}
		// then a text string, and then the label itself
		control.add( new JLabel( "Steps: "));
		// We use a specialised control called a spinner to display
		// the number of steps in use, which allows it to be changed
		// in an obvious way
		stepsSpinner = new SpinnerNumberModel( 
	        			defaultSteps, minSteps, maxSteps, 1);
		JSpinner spinner = new JSpinner(stepsSpinner);
		control.add( spinner);
		
		// Now for the lower JPanel.  Suggest a default size
		view.setPreferredSize( new Dimension( viewWidth, viewHeight));
		// Mouse commands do things here
		view.addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				base = new Point( e.getPoint());
				// check whether we are near to a triangle vertex
				// and, if so, enable dragging mode.  In this case
				// we re-order the points if necessary so that they
				// are in the same cyclic order but a is the point
				// being dragged
				// If we are not close to a vertex, we leave the
				// triangle vertices alone, and instead use the
				// dragged point to define the closest point on the
				// entire triangle, as new ratios to define the
				// segmentation to follow
				if ( near( base, a)) {
				    // do something (assignment 2)
					dragging=true;
				    a = base;
				    redraw();
				} else if ( near( base, b)) {
				    // do something (assignment 2)
					dragging=true;
				    b = base;//this meant to be a=base
				    redraw();
				} else if ( near( base, c)) {
				    // do something (assignment 2)
					dragging=true;
				    c = base; //this meant to be a=base
				    redraw();
				} else {
				    interior = true;
				    // do something (assignment 4)
				    setAndDrawMarkers();
				    redraw();
				}
			}

			public void mouseReleased(MouseEvent e) {
				dragging = interior = false;
				redraw();
			}
		});

		view.addMouseMotionListener( new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
				if ( dragging || interior ) {
					if ( dragging ) {
						base.setLocation( e.getPoint());
					} else if ( interior ) {
						centre = new Point( e.getPoint());
						}
					redraw();
				}				
			}   
		});


		view.addComponentListener(new java.awt.event.ComponentAdapter() 
		{
			public void componentResized(ComponentEvent e)
			{
			    rescale();
			}
		});

		// Create the Box, which we use to place one JPanel over
		// the other
		box = Box.createVerticalBox(); 
		box.add( control);		box.add( view);
		// Now add the Box (and so its contents) into this applet
		add( box);
	}

	// method to set the number of steps (into a text label)
	void setSteps( int n) {
		numSteps = Math.max( 0, n);
		stepsSpinner.setValue( numSteps);
	}
	
	// method to get the number of steps (from a text label)
	int getSteps() {
		return ((Number) stepsSpinner.getValue()).intValue();
	}
	
	private static void clear() {
		Graphics vg = view.getGraphics();
		Rectangle r = view.getBounds();
		vg.clearRect( 0, 0, r.width, r.height);
	}
	
	// draws a small box around the given point
	private static void drawMarker( Point p) {
		final int halfWidth = 5;
		Graphics g = view.getGraphics();
		g.drawLine(
				p.x-halfWidth, p.y-halfWidth,
				p.x-halfWidth, p.y+halfWidth);
		g.drawLine(
				p.x-halfWidth, p.y+halfWidth,
				p.x+halfWidth, p.y+halfWidth);
		g.drawLine(
				p.x+halfWidth, p.y+halfWidth,
				p.x+halfWidth, p.y-halfWidth);
		g.drawLine(
				p.x+halfWidth, p.y-halfWidth,
				p.x-halfWidth, p.y-halfWidth);
	}
	
	// Calls the recursive method dogasket
	public static void gasket( int steps) {
		dogasket( steps, (Graphics2D) view.getGraphics(), a, b, c);
	}

	private static void setEquilateralTriangle() {
		// Set default coordinates for the 3 triangle vertices;
		// these will need to be re-scaled to ensure that the
		// triangle fits within the view JPanel
		a = new Point( 0, 0);  b = new Point( 2000, 0);
		c = new Point( 1000, (int) -Math.floor(1000*Math.sqrt(3.0)));
	}
	
	private static void rescale() {
		// takes the current coordinates of the triangle vertices
		// [ a, b, c], and alters them to fit centrally within the
		// current setting for JPanel view
		final int margin = 20;
		Rectangle r = view.getBounds();
		double xsize = r.getWidth();
		double ysize = r.getHeight();
		double xlo = Math.min( a.x, Math.min( b.x, c.x));
		double xhi = Math.max( a.x, Math.max( b.x, c.x));
		double ylo = Math.min( a.y, Math.min( b.y, c.y));
		double yhi = Math.max( a.y, Math.max( b.y, c.y));
		double xscale = (xsize - 2.0*margin) / (xhi-xlo);
		double yscale = (ysize - 2.0*margin) / (yhi-ylo);
		double scale = Math.max( 0, Math.min( xscale, yscale));
		double xshift = (xsize - scale*(xhi + xlo)) / 2;
		double yshift = (ysize - scale*(yhi + ylo)) / 2;
		a.setLocation( xshift+scale*a.x, yshift+scale*a.y);
		b.setLocation( xshift+scale*b.x, yshift+scale*b.y);
		c.setLocation( xshift+scale*c.x, yshift+scale*c.y);
	}

	private static void dogasket( int steps, Graphics2D g, Point a, Point b, Point c) {
		int n = steps;
		if(n<=0){
	    drawTriangle( g, a, b, c);}
		else{
			//1
//			Point u=midPoint(a, b);
//			Point v=midPoint(a, c);
			Point ab=ratioPoint(abr,a, b);
			Point ac=ratioPoint(car, a, c);
			Point bc=ratioPoint(bcr, b, c);
			
			dogasket(n-1, g, a, ab, ac);
			
			//2
//			u=midPoint(b, c);
//			v=midPoint(b, a);
			
			
			dogasket(n-1, g, ab, b, bc);
			
			//3
			//u=midPoint(c, b);
			//v=midPoint(c, a);
			dogasket(n-1, g, ac, bc, c);
			
		}

	}

	// Determine whether one point is `near' another
	public static boolean near( Point p, Point t) {
		final int boxHalfWidth = 5;
		// how close do we have to be in both coordinate directions
		return (Math.abs( p.x-t.x)<=boxHalfWidth)
			&& (Math.abs( p.y-t.y)<=boxHalfWidth);
	}

	// construct the mid-point of two points
	public static Point midPoint( Point u, Point v) {
		return new Point( (u.x+v.x)/2, (u.y+v.y)/2);
	}
	
	public static Point ratioPoint(double r, Point u, Point v) {
		double s=1-r;
		int first = (int)((s*u.x+r*v.x));
		int second =(int)((s*u.y+r*v.y));
		return new Point( first, second);
//		return new Point( (u.x+v.x)*r, (u.y+v.y)*r);
	}
	
	private double ratioOf( Point p, Point a, Point b) {
		// returns the scalar r such that the foot of the
		// perpendicular from p to the extended line from a to b
		// is at point q, where q = (1-r)*a + r*b
		Point delta = new Point(b.x-a.x, b.y-a.y);
		Point pminusa = new Point(p.x-a.x, p.y-a.y);
		return scalarProduct( delta, pminusa)
				/ scalarProduct( delta, delta);
	}

	private double scalarProduct( Point a, Point b) {
		return (a.x*b.x) + (a.y*b.y);
	}
	
	// draw a filled triangle between the given points
	private static void drawTriangle( Graphics2D g, 
			     Point u, Point v, Point w) {
		g.fill( new Polygon(
				new int[] { u.x, v.x, w.x}, 
				new int[] { u.y, v.y, w.y}, 3));  		
	}

	// paint() is called when the entire applet needs re-painting
	public void paint( Graphics g) {
		control.repaint();
		redraw();
	}

	private void redraw() {
		clear();
		if ( dragging || interior ) {
			drawOneLine( a, b);
				drawOneLine( b, c);
			drawOneLine( c, a);
			if ( interior ) {
				setAndDrawMarkers();
				drawMarker(centre);
			}		
		}
		else {
		    gasket( getSteps());
		}
	}

	private void setAndDrawMarkers() {
	    // change for assignment 4
	    abr = ratioOf( centre, a, b);
	    Point ab  = ratioPoint(abr, a ,b);
	    
	    bcr = ratioOf( centre, b, c);
	    Point bc	= ratioPoint(bcr, b, c);
	    
	    car = ratioOf( centre, a, c);
	    Point ac 	= ratioPoint(car, a, c);
	    
	    drawMarker(ac);
	    drawMarker(bc);
	    drawMarker(ab);
	    
	   
	}

	// small private method to cyclically shift the information
	// about vertices a, b, and c.  Called once it shifts the
	// data from a->c, b->a and c->b; so called twice gives 
	// a->b, b->c and c->a
	private void rotateVertices() {
		Point temp;  double tempr;
		temp = a; a = b; b = c; c = temp;
		tempr = abr; abr = bcr; bcr = car; car = tempr;
	}
	
	// a small local method to draw a  single line
	private void drawOneLine( Point u, Point v) {
		Graphics g = view.getGraphics();
		g.drawLine( 
				(int) Math.floor( u.getX()), 
				(int) Math.floor( u.getY()), 
				(int) Math.floor( v.getX()), 
				(int) Math.floor( v.getY()));
	}
}
