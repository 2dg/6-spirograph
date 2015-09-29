import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class StraightLine extends JFrame {
	public StraightLine() {
		setLayout(new BorderLayout());
		JPanel graph = new Polar();
		Animator.animate(16, graph);
		add(graph, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		StraightLine frame = new StraightLine();
		frame.getContentPane().setPreferredSize(new Dimension(1000, 1000));
		frame.pack();
		frame.setTitle("Spirograph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

class Animator {
	static void animate(int ms, JPanel panel) {
		new Timer(ms, e -> panel.repaint()).start();
	}
}

class Pair<T> {
	public T left;
	public T right;

	public Pair(T left, T right) {
		this.left = left;
		this.right = right;
	}
}

class AppTime {
	static long epoch = System.currentTimeMillis();
	static long now() {
		return System.currentTimeMillis() - epoch;
	}
}

interface ParametricTransform {
	Pair<Double> transform(double t);
}

interface CoordinateTransform extends ParametricTransform {
	default Pair<Double> transform(double t) {
		return new Pair<>(x(t), y(t));
	}
	double x(double t);
	double y(double t);
}

interface RadiusTransform extends ParametricTransform {
	default Pair<Double> transform(double theta) {
		double r = fn(theta);
		return new Pair<>(r * Math.cos(theta), r * Math.sin(theta));
	}
	double fn(double theta);
}

class Polar extends JPanel {
	static double k = 400;

	static RadiusTransform sine = Math::sin;
	static ParametricTransform lissajous$5_4 = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(5 * t);
		}
		@Override
		public double y(double t) {
			return Math.sin(4 * t);
		}
	};
	static ParametricTransform lol = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin((AppTime.now() + 100) * t);
		}
		@Override
		public double y(double t) {
			return Math.sin(AppTime.now() * t);
		}
	};
	static ParametricTransform boom = t -> new Pair<>(
		Math.sin((AppTime.now() + 20) * t),
		Math.sin((AppTime.now()) * t)
	);
	static ParametricTransform rise = t -> new Pair<>(
			Math.sin((double)(AppTime.now()) / 10 * t),
			Math.sin(t)
	);
	static ParametricTransform lolS = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin((AppTime.now() + 1000) * t);
		}
		@Override
		public double y(double t) {
			return Math.sin(AppTime.now() * t);
		}
	};
	static ParametricTransform swarm = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(5 * t);
		}
		@Override
		public double y(double t) {
			return Math.sin((double)(System.currentTimeMillis()) / 20000 * t);
		}
	};
	static ParametricTransform oscillate = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(5 * t);
		}
		@Override
		public double y(double t) {
			return Math.sin((double)(System.currentTimeMillis()) / 2000 * t);
		}
	};
	static ParametricTransform castle = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(5 * t + ((double)(System.currentTimeMillis()) / 20000));
		}
		@Override
		public double y(double t) {
			return Math.sin((double)(System.currentTimeMillis()) / 20000 * t);
		}
	};
	static ParametricTransform flux = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(5 * t);
		}
		@Override
		public double y(double t) {
			return Math.sin(System.currentTimeMillis() * t);
		}
	};
	static ParametricTransform lissajous$10_9 = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(10 * t + (double)(System.currentTimeMillis()) / 2000);
		}
		@Override
		public double y(double t) {
			return Math.sin(9 * t);
		}
	};
	static ParametricTransform lissajous$10_9_1 = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(10 * t + Math.PI / 4 + (double)(System.currentTimeMillis()) / 2000);
		}
		@Override
		public double y(double t) {
			return Math.sin(9 * t);
		}
	};
	static ParametricTransform lissajous$10_9_2 = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(10 * t + Math.PI / 2 + (double)(System.currentTimeMillis()) / 2000);
		}
		@Override
		public double y(double t) {
			return Math.sin(9 * t);
		}
	};
	static ParametricTransform lissajous$10_9_3 = new CoordinateTransform() {
		@Override
		public double x(double t) {
			return Math.sin(10 * t + 3 * Math.PI / 4 + (double)(System.currentTimeMillis()) / 2000);
		}
		@Override
		public double y(double t) {
			return Math.sin(9 * t);
		}
	};

	void plot(Polygon p, double x, double y) {
		p.addPoint((int)(500 + k * x), (int)(500 - k * y));
	}

	void plotFn(Graphics2D g, int cycles, int granularity, ParametricTransform t) {
		Polygon p = new Polygon();
		double slice = cycles * 2 * Math.PI / granularity;
		for (int s = 0; s <= granularity; s++) {
			double theta = slice * s;
			Pair<Double> pair = t.transform(theta);
			plot(p, pair.left, pair.right);
		}
		g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);

		Graphics2D g = (Graphics2D)(gg);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawLine(0, 500, 1000, 500);
		g.drawLine(500, 0, 500, 1000);

//		g.setColor(Color.BLUE);
//		plotFn(g, 1, 600, castle);

//		g.setColor(Color.BLUE);
//		plotFn(g, 1, 1000, castle);

		g.setColor(Color.BLUE);
		plotFn(g, 1, 600, lol);

//		g.setColor(Color.BLUE);
//		plotFn(g, 1, 600, lissajous$10_9);
//
//		g.setColor(Color.GREEN);
//		plotFn(g, 1, 600, lissajous$10_9_1);
//
//		g.setColor(Color.RED);
//		plotFn(g, 1, 600, lissajous$10_9_2);
//
//		g.setColor(Color.ORANGE);
//		plotFn(g, 1, 600, lissajous$10_9_3);
	}
}


class Rectangular extends JPanel {
	static double k = 50;

	void plot(Polygon p, double x, double y) {
		p.addPoint((int)(500 + k * x), (int)(500 - k * y));
	}

	void plotFn(Graphics2D g, double start, double end, double step, Function<Double, Double> fn) {
		Polygon p = new Polygon();
		for (double x = start; x <= end; x += step) {
			plot(p, x, fn.apply(x));
		}
		g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
	}

	void plotFnReflect(Graphics2D g, double start, double end, double step, Function<Double, Double> fn) {
		plotFn(g, start, end, step, fn);
		plotFn(g, start, end, step, x -> -fn.apply(x));
	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);

		Graphics2D g = (Graphics2D)(gg);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawLine(0, 500, 1000, 500);
		g.drawLine(500, 0, 500, 1000);

		g.setColor(Color.BLUE);
		plotFnReflect(g, -10, 10, .1, Math::sin);

		g.setColor(Color.RED);
		plotFnReflect(g, -10, 10, .1, Math::cos);

		g.setColor(Color.GREEN);
		plotFnReflect(g, -10, 10, .1, Math::sinh);

		g.setColor(Color.ORANGE);
		plotFnReflect(g, -10, 10, .1, Math::cosh);

		g.setColor(Color.MAGENTA);
		plotFnReflect(g, -10, 10, .1, Math::tanh);
	}
}
