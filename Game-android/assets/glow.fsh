#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

varying vec2 v_texCoord;
varying float v_distance;

uniform vec3 u_glowColor;

void main() 
{

	gl_FragColor = vec4(u_glowColor, 1.0);
	gl_FragColor.a *= clamp(1.5 - v_distance / 100.0, 0.0, 1.0) * v_texCoord.y;
}