import type { ReactNode } from 'react'

interface PageHeaderProps {
	title: string
	children?: ReactNode
}

const PageHeader = (props: PageHeaderProps) => (
	<div className="d-flex justify-content-between align-items-center mb-4">
		<h1 className="mb-0">{props.title}</h1>
		{props.children}
	</div>
)

export default PageHeader
