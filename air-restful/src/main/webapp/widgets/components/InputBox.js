dojo.provide('technicolor.component.InputBox');
dojo.require('dijit._Widget');
dojo.require('dijit._Templated');
 
dojo.declare(
  'technicolor.component.InputBox',
  [dijit._Widget, dijit._Templated],
  {
    // DataList title
	oid : 'oid',
	olabel : 'olabel',
	ovalue : 'ovalue',
    dataUrl : '',
    templatePath : new dojo._Url('', './widgets/components/InputBox.html')
  }
);