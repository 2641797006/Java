package org.omg.PortableInterceptor;

/**
* org/omg/PortableInterceptor/ObjectReferenceTemplateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /build/openjdk-lts-Ae5PRQ/openjdk-lts-10.0.2+13/src/java.corba/share/classes/org/omg/PortableInterceptor/Interceptors.idl
* Friday, November 16, 2018 at 3:15:35 PM Coordinated Universal Time
*/


/** The object reference template.  An instance of this must
   * exist for each object adapter created in an ORB.  The server_id,
   * orb_id, and adapter_name attributes uniquely identify this template
   * within the scope of an IMR.  Note that adapter_id is similarly unique
   * within the same scope, but it is opaque, and less useful in many
   * cases.
   */
public final class ObjectReferenceTemplateHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.PortableInterceptor.ObjectReferenceTemplate value = null;

  public ObjectReferenceTemplateHolder ()
  {
  }

  public ObjectReferenceTemplateHolder (org.omg.PortableInterceptor.ObjectReferenceTemplate initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.omg.PortableInterceptor.ObjectReferenceTemplateHelper.type ();
  }

}
