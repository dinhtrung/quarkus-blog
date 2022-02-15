import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SampleAppSharedModule } from 'app/shared/shared.module';
import { BlogPostComponent } from './blog-post.component';
import { BlogPostDetailComponent } from './blog-post-detail.component';
import { BlogPostUpdateComponent } from './blog-post-update.component';
import { BlogPostDeleteDialogComponent } from './blog-post-delete-dialog.component';
import { blogPostRoute } from './blog-post.route';

@NgModule({
  imports: [SampleAppSharedModule, RouterModule.forChild(blogPostRoute)],
  declarations: [BlogPostComponent, BlogPostDetailComponent, BlogPostUpdateComponent, BlogPostDeleteDialogComponent],
  entryComponents: [BlogPostDeleteDialogComponent],
})
export class SampleAppBlogPostModule {}
