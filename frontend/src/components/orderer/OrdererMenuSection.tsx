import type { CSSProperties } from 'react'
import type { Meal, MealCategory } from '../../types'
import CategoryNavigation from './CategoryNavigation'
import OrdererMealCard from './OrdererMealCard'

interface OrdererMenuSectionProps {
	categories: MealCategory[]
	meals: Meal[]
	activeCategoryId: number | null
	addingMealId: number | null
	onCategorySelect: (categoryId: number) => void
	onMealAdd: (mealId: number) => void
}

const OrdererMenuSection = (props: OrdererMenuSectionProps) => {
	const activeCategory = props.categories.find(
		(category) => category.id === props.activeCategoryId,
	)
	const visibleMeals = props.meals.filter(
		(meal) => meal.mealCategoryId === props.activeCategoryId,
	)
	const sectionStyle: CSSProperties = activeCategory?.imageUrl
		? { backgroundImage: `url("${activeCategory.imageUrl}")` }
		: {}

	return (
		<section id="menu" className="orderer-section orderer-menu" style={sectionStyle}>
			<div className="orderer-menu-overlay" />
			<div className="orderer-menu-content">
				<header>
					<p>Nasze propozycje</p>
					<h2>Menu</h2>
				</header>
				<CategoryNavigation
					categories={props.categories}
					activeCategoryId={props.activeCategoryId}
					onSelect={props.onCategorySelect}
				/>
				<div className="orderer-meal-grid">
					{visibleMeals.map((meal) => (
						<OrdererMealCard
							key={meal.id}
							meal={meal}
							isAdding={meal.id === props.addingMealId}
							onAdd={props.onMealAdd}
						/>
					))}
					{!visibleMeals.length && (
						<p className="orderer-empty-menu">Brak posiłków w tej kategorii.</p>
					)}
				</div>
			</div>
		</section>
	)
}

export default OrdererMenuSection
