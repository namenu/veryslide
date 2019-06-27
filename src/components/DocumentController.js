import channel from '../core/Channel';
import { Page } from './Document';
import TextBox from './objects/TextBox';
import ImageBox from './objects/ImageBox';
import ImageList from './objects/ImageList';

class DocumentController {
  constructor(doc, editor) {
    this.doc = doc;
    this.editor = editor;
    this.page = null;
    this.object = null;
    this.clipboard = null;

    channel.bind(this, 'Document:addPage', () => {
      const newPage = this.doc.addPage();
      channel.send('PageList:addPage', newPage);
    });

    channel.bind(this, 'Document:selectPage', (page) => {
      this.page = page;
      this.object = null;
    });

    channel.bind(this, 'Document:removePage', () => {
      if (this.page == null) return;
      if (this.page == this.clipboard) { this.clipboard = null };

      channel.send('PageList:removePage', this.page);

      const nextpage = this.doc.removePage(this.page);
      this.page = nextpage;

      if (nextpage == null) {
        channel.send('Viewport:clear', nextpage);
      } else {
        channel.send('PageList:selectPage', nextpage);
      }
    });

    channel.bind(this, 'Document:addObject', (value) => {
      if (this.page == null) return;
      let newObject = this.page.addObject(value);
      channel.send('Viewport:focus', newObject);
    });

    channel.bind(this, 'Document:selectObject', (object) => {
      this.object = object;
    });

    channel.bind(this, 'Document:removeObject', () => {
      if (this.object == null) return;
      if (this.object == this.clipboard) { this.clipboard = null };
      this.page.removeObject(this.object);
      this.object = null;

      channel.send('Viewport:blur');
    });

    channel.bind(this, 'Document:orderBack', (object) => {
      if (this.page == null) return;
      this.page.objects.makeHead(object);
      this.page.reorder();
    });

    channel.bind(this, 'Document:orderFront', (object) => {
      if (this.page == null) return;
      this.page.objects.makeLast(object);
      this.page.reorder();
    });

    channel.bind(this, 'Document:orderBackward', (object) => {
      if (this.page == null) return;
      this.page.objects.backward(object);
      this.page.reorder();
    });

    channel.bind(this, 'Document:orderForward', (object) => {
      if (this.page == null) return;
      this.page.objects.forward(object);
      this.page.reorder();
    });

    channel.bind(this, 'Document:copy', () => {
      if (this.object != null) {
        this.clipboard = this.object;
      } else if (this.page != null) {
        this.clipboard = this.page;
      }
    });

    channel.bind(this, 'Document:paste', () => {
      if (this.clipboard == null) return;

      console.log('pasting', this.clipboard);
      let newObject = null;

      switch(this.clipboard.type) {
      /*
        case 'ImageList':
          newObject = new ImageList(this.clipboard.state);
          break;
        case 'ImageBox':
          this.panel = new PanelForImageBox({object});
          break;
          */
        case 'TextBox':
          newObject = new TextBox(this.clipboard.state);
          break;
          /*
        case 'Page':
          newObject = new Page(this.clipboard.state);
          break;
        case 'Document':
          this.panel = new PanelForDocument({object});
          break;
          */
      }

      if (newObject != null) {
        if (newObject.type == 'Page') {
          this.doc.appendPage(newObject);
          channel.send('PageList:addPage', newObject);
        } else {
          if (this.page != null) {
            this.page.appendObject(newObject);
            if (this.page == this.clipboard.page) {
              newObject.x += 10;
              newObject.y += 10;
            }
            this.clipboard = newObject;
            this.object = newObject;
            channel.send('Handler:connect', newObject);
          }
        }
      }
    });
  }
}

export default DocumentController
