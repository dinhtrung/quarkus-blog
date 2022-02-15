import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { BlogPostService } from './blog-post.service';

@Component({
  templateUrl: './blog-post-delete-dialog.component.html',
})
export class BlogPostDeleteDialogComponent {
  blogPost?: IBlogPost;

  constructor(protected blogPostService: BlogPostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.blogPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('blogPostListModification');
      this.activeModal.close();
    });
  }
}
