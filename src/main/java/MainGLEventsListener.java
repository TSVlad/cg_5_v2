import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.io.*;
import java.util.Date;

public class MainGLEventsListener implements GLEventListener {
    private double timeStart;
    private double time;
    private int shaderProgram;

    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glRotated(35, 1, 0, 0);

        initAllShaders(glAutoDrawable);
        timeStart = new Date().getTime();
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        time = (new Date().getTime() - timeStart) / 1000;
        gl.glUseProgram(shaderProgram);
        int t = gl.glGetUniformLocation(shaderProgram, "t");
        gl.glUniform1f(t, (float) time);

        for (double x = -0.8; x < 0.8; x+=0.01) {
            gl.glBegin(GL2.GL_QUADS);
            for (double z = -0.5; z < 0.5; z+=0.01) {
                double y = Math.cos(x * 10) * 0.1;
                double y1 = Math.cos((x + 0.01) * 10) * 0.1;
                double e = Math.exp(y)/(1 + Math.exp(y));
                gl.glColor3d(1.0 - e, e, 1.0 - e);

                gl.glVertex3d(x + 0.01, y1, z);
                gl.glVertex3d(x + 0.01, y1, z + 0.01);
                gl.glVertex3d(x, y, z + 0.01);
                gl.glVertex3d(x, y, z);
            }
            gl.glEnd();
        }
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }


    private void initAllShaders(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        shaderProgram = gl.glCreateProgram();
        initShader(glAutoDrawable, "/vertex_shader.glsl", GL2.GL_VERTEX_SHADER);
        initShader(glAutoDrawable, "/fragment_shader.glsl", GL2.GL_FRAGMENT_SHADER);
        gl.glLinkProgram(shaderProgram);
        gl.glUseProgram(shaderProgram);
    }

    private void initShader(GLAutoDrawable glAutoDrawable, String path, int shaderType) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        int shader = gl.glCreateShader(shaderType);
        StringBuilder code = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)))){
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line);
                code.append('\n');
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        System.out.println(code.toString());

        gl.glShaderSource(shader, 1, new String[]{code.toString()}, null);
        gl.glCompileShader(shader);
        gl.glAttachShader(shaderProgram, shader);
    }
}
