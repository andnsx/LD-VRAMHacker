
attribute vec3 a_position;

attribute vec2 a_texCoord;
varying vec2 v_texCoord;

uniform float u_cameraZ;
uniform mat4 u_worldTransform;
uniform mat4 u_projView;


varying float v_distance;

void main()
{
	v_texCoord = a_texCoord;
	
	vec4 pos = u_worldTransform * vec4(a_position, 1.0);
	gl_Position = u_projView * pos;
	
	// Alpha
	v_distance = pos.z - u_cameraZ;
}
