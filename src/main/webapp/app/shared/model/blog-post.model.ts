import { Moment } from 'moment';

export interface IBlogPost {
  id?: string;
  title?: string;
  summary?: any;
  figureContentType?: string;
  figure?: any;
  content?: any;
  category?: string;
  slug?: string;
  state?: number;
  weight?: number;
  publishedAt?: Moment;
  createdAt?: Moment;
  lastModifiedAt?: Moment;
  createdBy?: string;
  lastModifiedBy?: string;
  tags?: string;
}

export class BlogPost implements IBlogPost {
  constructor(
    public id?: string,
    public title?: string,
    public summary?: any,
    public figureContentType?: string,
    public figure?: any,
    public content?: any,
    public category?: string,
    public slug?: string,
    public state?: number,
    public weight?: number,
    public publishedAt?: Moment,
    public createdAt?: Moment,
    public lastModifiedAt?: Moment,
    public createdBy?: string,
    public lastModifiedBy?: string,
    public tags?: string
  ) {}
}
