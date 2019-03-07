package opentouhou.com.opentouhouandroid.graphics.opengl.opengles30.shader;

import android.opengl.GLES30;

import opentouhou.com.opentouhouandroid.graphics.opengl.common.shader.AbstractVertexShader;

public class VertexShader extends AbstractVertexShader
{
    public VertexShader(String name)
    {
        super(name);

        // Create a shader.
        handle = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);

        if (handle == 0)
        {
            throw new RuntimeException("Could not create shader. (glCreateShader)");
        }
    }

    // Implement Compilable
    public void compile()
    {
        // Compile the shader.
        GLES30.glCompileShader(handle);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES30.glGetShaderiv(handle, GLES30.GL_COMPILE_STATUS, compileStatus, 0);

        // Check if the compilation failed.
        if (compileStatus[0] == 0)
        {
            String err = GLES30.glGetShaderInfoLog(handle);
            throw new RuntimeException("Error compiling shader. " + err);
        }
    }

    public void compile(String code)
    {
        // Pass in the code.
        GLES30.glShaderSource(handle, code);

        if (handle == 0)
        {
            throw new RuntimeException("Could not set shader source. (glShaderSource)");
        }

        compile();
    }

    public void compile(StringBuffer codeBuffer)
    {
        compile(codeBuffer.toString());
    }

    // Implement AutoCloseable
    public void close()
    {
        GLES30.glDeleteShader(handle);
    }
}