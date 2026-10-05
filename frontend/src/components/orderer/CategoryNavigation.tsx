import type { MealCategory } from '../../types'

interface CategoryNavigationProps {
	categories: MealCategory[]
	activeCategoryId: number | null
	onSelect: (categoryId: number) => void
}

const CategoryNavigation = (props: CategoryNavigationProps) => (
	<nav className="orderer-category-navigation" aria-label="Kategorie menu">
		{props.categories.map((category) => (
			<button
				type="button"
				key={category.id}
				className={category.id === props.activeCategoryId ? 'is-active' : ''}
				onClick={() => props.onSelect(category.id)}
				title={category.name}
			>
				{category.name}
			</button>
		))}
	</nav>
)

export default CategoryNavigation
