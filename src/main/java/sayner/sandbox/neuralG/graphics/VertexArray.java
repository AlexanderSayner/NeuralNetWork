package sayner.sandbox.neuralG.graphics;

import sayner.sandbox.neuralG.utils.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    private int count;
    private int vao; // Vertex Array Object
    private int vbo; // Vertex Buffer Object
    private int ibo; // Index Buffer Object
    private int tbo; // Texture Buffer Object

    public VertexArray(float[] vertices,byte[] indices,float[] textureCoordinates){

        this.count=indices.length;

        // Create and bind vertex array object
        this.vao=glGenVertexArrays();
        glBindVertexArray(this.vao);

        // Create and bind vertex buffer object
        this.vbo=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,this.vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices),GL_STATIC_DRAW);
        // x,y,z - that is why 3
        glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE_LOCATION,3,GL_FLOAT,false,0,0); // Every 3 components it goes x coordinate
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE_LOCATION);

        // Create and bind texture buffer object
        this.tbo=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,this.tbo);
        glBufferData(GL_ARRAY_BUFFER,BufferUtils.createFloatBuffer(textureCoordinates),GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TextureCOORD_ATTRIBUTE_LOCATION,2,GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(Shader.TextureCOORD_ATTRIBUTE_LOCATION);

        // Create and bind index buffer object
        this.ibo=glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,BufferUtils.createByteBuffer(indices),GL_STATIC_DRAW);

        // Now it can be unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindVertexArray(0);
    }

    public void bind(){

        glBindVertexArray(this.vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,this.ibo);
    }

    public void unbind(){

        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public void draw(){

        glDrawElements(GL_TRIANGLES,this.count,GL_UNSIGNED_BYTE,0);
    }

    public void render(){

        bind();
        draw();
    }
}
